package com.psr.seatservice.dto.program.request;

import lombok.Getter;

import java.util.Date;

@Getter
public class AdminAddProgramRequest {
    private String title; //프로그램 제목
    private String place; //장소
    private String target; //신청대상
    private Date startDate; //신청시작일
    private Date endDate; //신청마감일
}
