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
    private String filename;
    private String target;
    private String targetDetail;

    public ProgramListResponse(Program program) {
        this.programNum = program.getProgramNum();
        this.title = program.getTitle();
        this.startDate = program.getStartDate();
        this.endDate = program.getEndDate();
        this.type = program.getType();
        this.place = program.getPlace();
    }

    public ProgramListResponse(Long programNum, String title, Date startDate, Date endDate, String type, String place, String filename, String target, String targetDetail) {
        this.programNum = programNum;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.place = place;
        this.filename = filename;
        this.target = target;
        this.targetDetail = targetDetail;
    }
}
