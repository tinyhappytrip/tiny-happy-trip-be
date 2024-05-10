package com.tinyhappytrip.smtp.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "mrcsbin@gmail.com";
    private static int number;

    public int sendMail(String mail) throws Exception {
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);
        return number;
    }

    public static void createNumber() {
        number = (int) (Math.random() * (90000)) + 100000;
    }

    public MimeMessage CreateMail(String mail) throws Exception {
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(message, "UTF-8");
        mailHelper.setFrom(senderEmail, "소확행");
        mailHelper.setTo(mail);
        mailHelper.setSubject("이메일 인증");
        String body = "";
        body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
        body += "<h1>" + number + "</h1>";
        body += "<h3>" + "감사합니다." + "</h3>";
        mailHelper.setText(body, true);
        return message;
    }
}