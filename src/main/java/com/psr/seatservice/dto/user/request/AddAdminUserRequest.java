package com.psr.seatservice.dto.user.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAdminUserRequest {
    private String userId;
    private String password;
    private String name;
    private String address;
    private String phone;
}
