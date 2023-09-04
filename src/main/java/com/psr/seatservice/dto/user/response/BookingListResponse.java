package com.psr.seatservice.dto.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingListResponse {
    private String programName;
    private String viewingDate;
    private String viewingTime;
    private Long bookingNum;

    public BookingListResponse(String programName, String viewingDate, String viewingTime, Long bookingNum){
        this.programName = programName;
        this.viewingDate = viewingDate;
        this.viewingTime = viewingTime;
        this.bookingNum = bookingNum;
    }
}
