package com.psr.seatservice.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class AddUserRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    private String address;
    @NotBlank(message = "주소를 입력해주세요.")
    private String addr1;

    private String addr2;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 형식이 아닙니다.")
    private String phone;
    private Integer phone1;
    private Integer phone2;
    private Integer phone3;

    @Email(message = "올바른 형식이 아닙니다.")
    private String email;
    private String email1;
    private String email2;

    private String role;

    @Pattern(regexp = "\\d{0}|\\d{8}", message = "올바른 형식이 아닙니다.(YYYYMMDD)")
    private String birth;
}
