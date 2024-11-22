package com.tinyhappytrip.smtp.service;

import com.tinyhappytrip.user.domain.User;
import com.tinyhappytrip.user.domain.enums.SocialType;
import com.tinyhappytrip.user.mapper.UserMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private static final String senderEmail = "mrcsbin@gmail.com";
    private static int number;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 12;

    public int sendMail(String email) throws Exception {
        MimeMessage message = CreateAuthMail(email);
        javaMailSender.send(message);
        return number;
    }

    public SocialType sendPasswordMail(String email) throws Exception {
        Optional<User> userOptional = userMapper.selectByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getSocialType() == SocialType.EMAIL) {
                String newPassword = generateRandomPassword();
                resetUserPassword(user, newPassword);
                MimeMessage message = CreateFindMail(email, newPassword);
                javaMailSender.send(message);
                return user.getSocialType();
            }
            return user.getSocialType();
        }
        return null;
    }

    private void createNumber() {
        number = (int) (Math.random() * (90000)) + 100000;
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    private void resetUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.update(user);
    }


    public MimeMessage CreateAuthMail(String mail) throws Exception {
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

    public MimeMessage CreateFindMail(String mail, String newPassword) throws Exception {
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mailHelper = new MimeMessageHelper(message, "UTF-8");
        mailHelper.setFrom(senderEmail, "소확행");
        mailHelper.setTo(mail);
        mailHelper.setSubject("비밀번호 찾기");
        String body = "";
        body += "<h3>" + "변경 비밀번호 : " + "</h3>";
        body += "<h1>" + newPassword + "</h1>";
        body += "<h3>" + "로그인 하신 후 비밀번호를 변경해주세요." + "</h3>";
        mailHelper.setText(body, true);
        return message;
    }
}