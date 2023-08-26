package com.psr.seatservice.dto.program.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BizUpdateProgramBookingStatusRequest {
    private List<String> bookingNumList;
    private String status;
    private String reason;
}
