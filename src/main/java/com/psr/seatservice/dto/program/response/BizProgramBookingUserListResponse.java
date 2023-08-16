package com.psr.seatservice.dto.program.response;

import lombok.Getter;

@Getter
public class BizProgramBookingUserListResponse {
    private String bookingDate;
    private String name;
    private String phone;
    private String birth;
    private Integer seatNum;
    private String status;
}
