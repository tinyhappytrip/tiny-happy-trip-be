package com.tinyhappytrip.user.mapper;

import com.tinyhappytrip.security.jwt.JwtToken;

public interface TokenMapper {
    void insert(JwtToken token);
}
