package com.psr.seatservice.controller;

import com.psr.seatservice.dto.CheckEmailRequest;
import com.psr.seatservice.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/join/email")
    public String checkEmail(@RequestBody CheckEmailRequest request) {
        String num = emailService.sendEmail(request.getUserEmail());
        return num;
    }
}
