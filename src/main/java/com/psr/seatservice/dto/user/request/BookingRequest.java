package com.psr.seatservice.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private Integer seatNum;
    private String viewingDate;
    private String viewingTime;
    private String programResponse;
}
