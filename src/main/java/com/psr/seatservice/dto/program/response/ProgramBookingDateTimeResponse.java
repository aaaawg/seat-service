package com.psr.seatservice.dto.program.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramBookingDateTimeResponse {
    private String viewingDate;
    private String viewingTime;
    public ProgramBookingDateTimeResponse(String viewingDate, String viewingTime){
        this.viewingDate = viewingDate;
        this.viewingTime = viewingTime;
    }
}
