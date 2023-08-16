package com.psr.seatservice.dto.program.response;

import com.psr.seatservice.domian.program.Program;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class BizProgramListResponse {
    private Long programNum;
    private String title;
    private Timestamp createDate;

    public BizProgramListResponse(Program program) {
        this.programNum = program.getProgramNum();
        this.title = program.getTitle();
        this.createDate = program.getCreateDate();
    }
}
