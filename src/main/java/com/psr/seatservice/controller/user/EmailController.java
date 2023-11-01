package com.psr.seatservice.controller.user;

import com.psr.seatservice.service.user.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/join/email")
    public String checkEmail(@RequestParam String email) {
        String num = emailService.sendEmail(email);
        return num;
    }
}
