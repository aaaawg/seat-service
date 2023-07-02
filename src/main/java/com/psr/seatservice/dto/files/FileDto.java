package com.psr.seatservice.dto.files;
import com.psr.seatservice.domian.files.Files;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {
    private String origfilename;
    private String filename;
    private String filepath;

    public FileDto(String origFilename, String filename, String filePath) {
        this.origfilename = origFilename;
        this.filename = filename;
        this.filepath = filePath;
    }
}
