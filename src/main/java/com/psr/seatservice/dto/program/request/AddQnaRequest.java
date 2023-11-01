package com.psr.seatservice.dto.program.request;

import lombok.Getter;

@Getter
public class AddQnaRequest {
    private String question;
    private boolean secret;
}
