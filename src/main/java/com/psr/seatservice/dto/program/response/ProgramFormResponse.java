package com.psr.seatservice.dto.program.response;

import lombok.Getter;

@Getter
public class ProgramFormResponse {
    private String programHtml;
    public ProgramFormResponse(String programHtml){
        this.programHtml = programHtml;
    }
}
