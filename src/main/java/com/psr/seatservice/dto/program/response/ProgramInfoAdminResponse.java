package com.psr.seatservice.dto.program.response;

import com.psr.seatservice.domian.program.Program;
import lombok.Getter;

import java.util.Date;

@Getter
public class ProgramInfoAdminResponse {
    private Long programNum;
    private String title; //프로그램 제목
    private String place; //장소
    private String target; //신청대상
    private Date startDate; //신청시작일
    private Date endDate; //신청마감일

    public ProgramInfoAdminResponse(Program program) {
        this.programNum = program.getProgramNum();
        this.title = program.getTitle();
        this.place = program.getPlace();
        this.target = program.getTarget();
        this.startDate = program.getStartDate();
        this.endDate = program.getEndDate();
    }
}
