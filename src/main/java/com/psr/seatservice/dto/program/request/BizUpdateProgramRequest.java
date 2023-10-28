package com.psr.seatservice.dto.program.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BizUpdateProgramRequest {
    //기업 사용자 - 프로그램 정보 수정
    private String title; //프로그램 제목
    private String place; //장소
    private String target; //신청대상
    private String way;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate; //신청시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate; //신청마감일
    private List<String> viewingDateAndTime;
    //private Long fileId;//poster
    private String type;
    private int peopleNum;
    private Integer seatCol;
    private String seatingChart;
    private String contents;
    private String targetDetail;
}
