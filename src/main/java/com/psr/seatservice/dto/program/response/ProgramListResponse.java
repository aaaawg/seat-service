package com.psr.seatservice.dto.program.response;

import lombok.Getter;

import java.util.Date;

@Getter
public class ProgramListResponse {
    private Long programNum;
    private String title;
    private Date startDate;
    private Date endDate;
    private String place;
    private String type;
    private String filename;
    private String target;
    private String targetDetail;

    public ProgramListResponse(Long programNum, String title, Date startDate, Date endDate, String place, String type, String filename, String target, String targetDetail) {
        this.programNum = programNum;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.filename = filename;
        this.target = target;
        this.targetDetail = targetDetail;
        this.place = place;
    }
}
