package com.psr.seatservice.dto.program.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramSeatingChartRequest {
    private Long programNum;
    private String viewingDate;
    private String viewingTime;
}
