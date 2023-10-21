package com.psr.seatservice.dto.program.request;

import lombok.Getter;

@Getter
public class PeopleCountRequest {
    private Long programNum;
    private String viewingDate;
    private String viewingTime;
}
