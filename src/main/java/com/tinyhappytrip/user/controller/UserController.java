package com.tinyhappytrip.user.controller;

import com.tinyhappytrip.security.jwt.JwtToken;
import com.tinyhappytrip.security.util.SecurityUtil;
import com.tinyhappytrip.user.dto.UserRequest;
import com.tinyhappytrip.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/members/login")
    public JwtToken login(@RequestBody UserRequest.Login login) {
        String email = login.getEmail();
        String password = login.getPassword();
        JwtToken jwtToken = userService.login(email, password);
        log.info("request email = {}, password = {}", email, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/admin/test")
    public String admin() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        System.out.println("currentUsername = " + currentUsername);
        return currentUsername;
    }

    @PostMapping("/members/test")
    public String member() {
        String currentUsername = SecurityUtil.getCurrentUsername();
        System.out.println("currentUsername = " + currentUsername);
        return currentUsername;
    }
}
