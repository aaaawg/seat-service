package com.psr.seatservice.dto.program.response;

import com.psr.seatservice.domian.program.Program;
import lombok.Getter;

import java.util.Date;

@Getter
public class ProgramInfoUpdateResponse {
    //프로그램 상세정보
    private Long programNum;
    private String title; //프로그램 제목
    private String place; //장소
    private String target; //신청대상
    private Date startDate; //신청시작일
    private Date endDate; //신청마감일
    private String type;
    private String way;
    private int peopleNum;
    private Integer seatCol;
    private String seatingChart;
    private String contents;
    private String targetDetail;

    public ProgramInfoUpdateResponse(Program program) {
        this.programNum = program.getProgramNum();
        this.title = program.getTitle();
        this.place = program.getPlace();
        this.target = program.getTarget();
        this.startDate = program.getStartDate();
        this.endDate = program.getEndDate();
        this.type = program.getType();
        this.peopleNum = program.getPeopleNum();
        this.seatCol = program.getSeatCol();
        this.seatingChart = program.getSeatingChart();
        this.way = program.getWay();
        this.contents = program.getContents();
        this.targetDetail = program.getTargetDetail();
    }
}
