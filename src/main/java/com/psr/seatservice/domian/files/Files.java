package com.psr.seatservice.domian.files;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origfilename;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filepath;

    public Files(String origFilename, String filename, String filePath) {
        this.origfilename = origFilename;
        this.filename = filename;
        this.filepath = filePath;
    }
}
