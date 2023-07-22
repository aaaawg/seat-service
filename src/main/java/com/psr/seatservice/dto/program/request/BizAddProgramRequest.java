package com.psr.seatservice.dto.program.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class BizAddProgramRequest {
    //기업 사용자 - 프로그램 정보 추가
    private String title; //프로그램 제목
    private String place; //장소
    private String target; //신청대상
    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate; //신청시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate; //신청마감일
    private List<String> viewingDateAndTime;
    private List<String> imageFiles;
    private String seatingChart;
}
