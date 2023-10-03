package com.psr.seatservice.dto.program.request;

import lombok.Getter;

@Getter
public class AddQnaRequest {
    private String content;
    private boolean secret;
}
