package com.psr.seatservice.dto.user.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingListResponse {
    private String programName;
    private String viewingDate;
    private String viewingTime;

    public BookingListResponse(String programName, String viewingDate, String viewingTime){
        this.programName = programName;
        this.viewingDate = viewingDate;
        this.viewingTime = viewingTime;
    }
}
