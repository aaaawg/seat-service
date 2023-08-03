package com.psr.seatservice.dto.program.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ProgramBookingInfoResponse {
    private String seatingChart; //좌석표 배열
    private Long bookingCount; //예약건수
    private List<Integer> bookingSeatList; //예약된 좌석번호
    private Integer seatCol;

    public ProgramBookingInfoResponse(Long bookingCount) {
        this.bookingCount = bookingCount;
    }

    public ProgramBookingInfoResponse(String seatingChart, Integer seatCol, Long bookingCount, List<Integer> bookingSeatList) {
        this.seatingChart = seatingChart;
        this.seatCol = seatCol;
        this.bookingCount = bookingCount;
        this.bookingSeatList = bookingSeatList;
    }
}
