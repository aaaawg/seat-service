package com.psr.seatservice.dto.program.response;

import com.psr.seatservice.domian.program.Program;
import lombok.Getter;

@Getter
public class BizProgramListResponse {
    private Long programNum;
    private String title;

    public BizProgramListResponse(Program program) {
        this.programNum = program.getProgramNum();
        this.title = program.getTitle();
    }
}
