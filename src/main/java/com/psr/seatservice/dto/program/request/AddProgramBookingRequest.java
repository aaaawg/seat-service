package com.psr.seatservice.dto.program.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProgramBookingRequest {

    private Integer seatNum;
    private String viewingDate;
    private String viewingTime;
    //private String userId;
}
