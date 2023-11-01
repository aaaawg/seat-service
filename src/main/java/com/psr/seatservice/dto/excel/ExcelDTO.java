package com.psr.seatservice.dto.excel;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ExcelDTO {
    private String name;
    private String birth;
    private String phone;
    private String email;
    private Date bookingDate;
    private String programQuestion;
    private String programResponse;
    public ExcelDTO(String name,String birth,String phone,String email,Date bookingDate, String programQuestion, String programResponse) {
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.email = email;
        this.bookingDate = bookingDate;
        this.programQuestion = programQuestion;
        this.programResponse = programResponse;
    }
}
