package com.psr.seatservice.dto.program.response;

import lombok.Getter;

import java.util.List;

@Getter
public class BizProgramBookingInfoResponse {

    private String seatingChart; //좌석표 배열
    private List<Integer> bookingSeatList; //예약된 좌석번호
    private Integer seatCol;
    private String title;
    private String date;
    private String time;

    public BizProgramBookingInfoResponse(String seatingChart, List<Integer> bookingSeatList, Integer seatCol, String title, String date, String time) {
        this.seatingChart = seatingChart;
        this.bookingSeatList = bookingSeatList;
        this.seatCol = seatCol;
        this.title = title;
        this.date = date;
        this.time = time;
    }
}
