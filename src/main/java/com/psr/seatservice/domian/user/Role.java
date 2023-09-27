package com.psr.seatservice.domian.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    BIZ("ROLE_BIZ");

    private String value;
}
