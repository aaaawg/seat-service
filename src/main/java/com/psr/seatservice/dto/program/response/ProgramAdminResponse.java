package com.psr.seatservice.dto.program.response;

import com.psr.seatservice.domian.program.Program;
import lombok.Getter;

@Getter
public class ProgramAdminResponse {
    private Long programNum;
    private String title;

    public ProgramAdminResponse(Program program) {
        this.programNum = program.getProgramNum();
        this.title = program.getTitle();
    }
}
