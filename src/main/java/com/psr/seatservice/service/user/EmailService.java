package com.psr.seatservice.service.user;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String createRandomNum() {
        Random random = new Random();
        int num;
        String ranNum = "";
        String result = "";

        for (int i = 0; i < 6; i++) {
            num = random.nextInt(9);
            ranNum = Integer.toString(num);
            result += ranNum;
        }
        return result;
    }

    public String sendEmail(String toEmail) {
        String randomNum = createRandomNum();
        String setFrom = ""; //메일입력
        String title = "[psr] 인증번호는 " + randomNum + "입니다.";
        String message = "안녕하세요.\n 아래 인증번호를 인증번호 입력칸에 입력해주세요.\n\n" + "인증번호: " + randomNum;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(setFrom);
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(message);

/*        new Thread(new Runnable() {
            @Override
            public void run() {
                mailSender.send(simpleMailMessage); //메일을 실제로 보냄
            }
        }).start();*/

        return randomNum;
    }
}
