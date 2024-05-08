package com.tinyhappytrip.user.service;

import com.tinyhappytrip.security.jwt.JwtToken;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    JwtToken login(String email, String password);
}
