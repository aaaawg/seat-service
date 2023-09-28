package com.psr.seatservice.dto.program.response;

import com.psr.seatservice.domian.program.Program;
import lombok.Getter;

import java.util.Date;

@Getter
public class ProgramInfoResponse {
    //프로그램 상세정보
    private Long programNum;
    private String title; //프로그램 제목
    private String place; //장소
    private String target; //신청대상
    private String targetDetail; //신청대상
    private Date startDate; //신청시작일
    private Date endDate; //신청마감일
    private String type;
    private int peopleNum;
    private String contents; //상세정보
    private String userPhone;

    public ProgramInfoResponse(Program program) {
        this.programNum = program.getProgramNum();
        this.title = program.getTitle();
        this.place = program.getPlace();
        this.target = program.getTarget();
        this.targetDetail = program.getTargetDetail();
        this.startDate = program.getStartDate();
        this.endDate = program.getEndDate();
        this.type = program.getType();
        this.peopleNum = program.getPeopleNum();
        this.contents = program.getContents();
        this.userPhone = program.getUser().getPhone();
    }
}
