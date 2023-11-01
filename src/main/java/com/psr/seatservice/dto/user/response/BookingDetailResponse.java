package com.psr.seatservice.dto.user.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookingDetailResponse {
    private Long programNum;
    private String title;
    private Date endDate;
    private String place;
    private String type;
    private String way;
    private Date bookingDate;
    private Date updateDate;
    private String reason;
    private String status;
    private String viewingDate;
    private String viewingTime;
    private Integer seatNum;
    private Long bookingNum;
    private String filename;
    private String yDate; //endDate의 하루 전

    public BookingDetailResponse(Long programNum, String title, Date endDate, String place, String type, String way, Date bookingDate, Date updateDate, String reason, String status, String viewingDate, String viewingTime, Integer seatNum, Long bookingNum) {
        this.programNum = programNum;
        this.title = title;
        this.endDate = endDate;
        this.place = place;
        this.type = type;
        this.way = way; //온라인-참여방법
        this.bookingDate = bookingDate;
        this.updateDate = updateDate;
        this.reason = reason; //프로그램 등록자 - 취소사유
        this.status = status;
        this.viewingDate = viewingDate;
        this.viewingTime = viewingTime;
        this.seatNum = seatNum;
        this.bookingNum = bookingNum;
    }
}
