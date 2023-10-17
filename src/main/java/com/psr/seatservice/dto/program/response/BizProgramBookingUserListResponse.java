package com.psr.seatservice.dto.program.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Getter
@NoArgsConstructor
public class BizProgramBookingUserListResponse {
    private String name;
    private String phone;
    private String birth;
    private String email;
    private String address;
    private Integer seatNum;
    private Long bookingNum;
    private Date bookingDate;
    private Date updateDate;
    private String status;

    public BizProgramBookingUserListResponse(String name, String phone, String birth, String email, String address, Integer seatNum, Long bookingNum, Date bookingDate, Date updateDate, String status) {
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.email = email;
        this.address = address;
        this.seatNum = seatNum;
        this.bookingNum = bookingNum;
        this.bookingDate = bookingDate;
        this.updateDate = updateDate;
        this.status = status;
    }
}
