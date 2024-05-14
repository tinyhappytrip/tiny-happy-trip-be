package com.tinyhappytrip.smtp.controller;

import com.tinyhappytrip.smtp.dto.EmailRequestDto;
import com.tinyhappytrip.smtp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/signup")
    public String signUpMailSend(@RequestBody EmailRequestDto.SignUp signUp) throws Exception {
        int number = emailService.sendMail(signUp.getEmail());
        return "" + number;
    }

    @PostMapping("/find-pw")
    public void findMailSend(@RequestBody EmailRequestDto.findPassword findPassword) throws Exception {
        int number = emailService.sendMail(findPassword.getEmail());
    }
}