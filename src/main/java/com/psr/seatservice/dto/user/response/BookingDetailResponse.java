package com.psr.seatservice.dto.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingDetailResponse {
    private String programName;
    private String viewingDate;
    private String viewingTime;
    private String place;
    private String type;
    private Integer seatNum;
    private Long bookingNum;

    public BookingDetailResponse(String programName, String viewingDate, String viewingTime, String place, String type, Integer seatNum, Long bookingNum) {
        this.programName = programName;
        this.viewingDate = viewingDate;
        this.viewingTime = viewingTime;
        this.place = place;
        this.type = type;
        this.seatNum = seatNum;
        this.bookingNum = bookingNum;
    }
}
