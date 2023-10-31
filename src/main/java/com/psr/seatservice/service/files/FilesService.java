package com.psr.seatservice.service.files;

import com.psr.seatservice.domian.files.Files;
import com.psr.seatservice.domian.files.FilesRepository;
import com.psr.seatservice.dto.files.FileDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FilesService {
    private String fileDir = System.getProperty("user.dir") + "\\files" + "\\";
    private final FilesRepository filesRepository;

    public FilesService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public Long saveFiles(MultipartFile files, Long proNum) throws IOException{
        if (files.isEmpty()) {
            return null;
        }
        System.out.println(files.getOriginalFilename());
        // 원래 파일 이름 추출
        String origName = files.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + savedName;

        // 파일 엔티티 생성
        Files file = new Files(origName,savedName,savedPath, proNum);

        // 실제로 로컬에 uuid를 파일명으로 저장
        files.transferTo(new File(savedPath));

        // 데이터베이스에 파일 정보 저장
        Files savedFile = filesRepository.save(file);

        return savedFile.getId();
    }

    public FileDto getFile(Long id) {
        Files files = filesRepository.findByPostId(id);
        if(files==null) return null;
        FileDto fileDto = new FileDto(files.getOrigfilename(), files.getFilename(), files.getFilepath());
        return fileDto;
    }
    public List<FileDto> getFileByProNum(Long id) {
        List<Files> list = new ArrayList<>();
        list = filesRepository.findBypostId(id);

        List<FileDto> re = new ArrayList<>();
        for (Files a: list) {
            FileDto dto = new FileDto(a.getOrigfilename(),a.getFilename(),a.getFilepath());
            re.add(dto);
        }

        if(re==null) return null;
        return re;
    }
    @Transactional
    public void deleteFile(Long postId){filesRepository.deleteByPostId(postId);}
}
