package com.tinyhappytrip.smtp.controller;

import com.tinyhappytrip.smtp.service.EmailService;
import com.tinyhappytrip.user.domain.enums.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/signup/{email}")
    public String signUpMailSend(@PathVariable String email) throws Exception {
        int number = emailService.sendMail(email);
        return "" + number;
    }

    @PostMapping("/find/{email}")
    public ResponseEntity<SocialType> findMailSend(@PathVariable String email) throws Exception {
        return ResponseEntity.ok(emailService.sendPasswordMail(email));
    }
}