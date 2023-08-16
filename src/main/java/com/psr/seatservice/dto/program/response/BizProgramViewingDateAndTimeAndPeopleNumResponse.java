package com.psr.seatservice.dto.program.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BizProgramViewingDateAndTimeAndPeopleNumResponse {
    private String viewingDate;
    private String viewingTime;
    private long peopleNum;

    public BizProgramViewingDateAndTimeAndPeopleNumResponse(String viewingDate, String viewingTime, long peopleNum) {
        this.viewingDate = viewingDate;
        this.viewingTime = viewingTime;
        this.peopleNum = peopleNum;
    }
}
