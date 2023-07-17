package com.psr.seatservice.domian.files;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private Long postId;

    public Files(String origFilename, String filename, String filePath, Long postId) {
        this.origfilename = origFilename;
        this.filename = filename;
        this.filepath = filePath;
        this.postId = postId;
    }
}
