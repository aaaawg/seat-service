package com.psr.seatservice.dto.program.response;

import com.psr.seatservice.domian.program.ProgramViewing;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProgramViewingDateAndTimeResponse {
    private Long programNo;
    private String viewingDate;
    private String viewingTime;

    public ProgramViewingDateAndTimeResponse(ProgramViewing programViewing) {
        this.programNo = programViewing.getProgramNo();
        this.viewingDate = programViewing.getViewingDate();
        this.viewingTime = programViewing.getViewingTime();
    }
}
