package com.psr.seatservice.dto.program.response;

import com.psr.seatservice.domian.program.Program;
import lombok.Getter;

@Getter
public class AdminProgramResponse {
    private Long programNum;
    private String title;

    public AdminProgramResponse(Program program) {
        this.programNum = program.getProgramNum();
        this.title = program.getTitle();
    }
}
