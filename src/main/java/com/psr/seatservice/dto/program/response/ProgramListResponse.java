package com.psr.seatservice.dto.program.response;

import com.psr.seatservice.domian.program.Program;
import lombok.Getter;

import java.util.Date;

@Getter
public class ProgramListResponse {
    private Long programNum;
    private String title;
    private Date startDate;
    private Date endDate;
    private String type;
    private String place;

    public ProgramListResponse(Program program) {
        this.programNum = program.getProgramNum();
        this.title = program.getTitle();
        this.startDate = program.getStartDate();
        this.endDate = program.getEndDate();
        this.type = program.getType();
        this.place = program.getPlace();
    }
}
