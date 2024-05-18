package com.tinyhappytrip.smtp.controller;

import com.tinyhappytrip.smtp.dto.EmailRequestDto;
import com.tinyhappytrip.smtp.service.EmailService;
import com.tinyhappytrip.user.domain.enums.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/find/{email}")
    public ResponseEntity<SocialType> findMailSend(@PathVariable String email) throws Exception {
        return ResponseEntity.ok(emailService.sendPasswordMail(email));
    }
}