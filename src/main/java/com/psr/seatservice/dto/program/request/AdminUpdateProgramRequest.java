package com.psr.seatservice.dto.program.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class AdminUpdateProgramRequest {
    private String title; //프로그램 제목
    private String place; //장소
    private String target; //신청대상
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate; //신청시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate; //신청마감일
}
