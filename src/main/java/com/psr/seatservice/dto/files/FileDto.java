package com.psr.seatservice.dto.files;
import com.psr.seatservice.domian.files.Files;
import lombok.*;
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {
    private String origfilename; //원이름
    private String filename; //새로운이름
    private String filepath; //경로

    public FileDto(String origFilename, String filename, String filePath) {
        this.origfilename = origFilename;
        this.filename = filename;
        this.filepath = filePath;
    }
    public static FileDto fromEntity(FileDto file){
        return FileDto.builder()
                .origfilename(file.getOrigfilename())
                .filename(file.getFilename())
                .filepath(file.getFilepath())
                .build();
    }
}
