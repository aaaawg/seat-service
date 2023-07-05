package com.psr.seatservice.domian.program;

import java.time.LocalDateTime;

public class ProgramBooking {
    //개인 사용자가 프로그램 예약한 정보
    private Long bookingNum;
    private Long programNum;
    private String userId;
    private Integer seatNum;
    private LocalDateTime bookingDate;
}
