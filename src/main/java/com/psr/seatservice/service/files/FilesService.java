package com.psr.seatservice.service.files;
import com.psr.seatservice.domian.files.Files;
import com.psr.seatservice.domian.files.FilesRepository;
import com.psr.seatservice.dto.files.FileDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FilesService {
    private final FilesRepository filesRepository;

    public FilesService(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }
    @Transactional
    public Long saveFile(FileDto fileDto) {
        Files files = new Files(fileDto.getOrigfilename(), fileDto.getFilename(), fileDto.getFilepath());
        System.out.println(fileDto.getOrigfilename()+ fileDto.getFilename()+ fileDto.getFilepath());
        Long id = 1L;
        id = filesRepository.save(files).getId();
        if(id == null) {
            System.out.println("Null is hear");
            return 0L;
        }
        return id;
    }

   /* @Transactional
    public FileDto getFile(Long id) {
        Files files = filesRepository.findById(id).get();

        FileDto fileDto = FileDto.builder()
                .origFilename(files.getOrigFilename())
                .filename(files.getFilename())
                .filePath(files.getFilePath())
                .build();
        return fileDto;
    }*/
}
