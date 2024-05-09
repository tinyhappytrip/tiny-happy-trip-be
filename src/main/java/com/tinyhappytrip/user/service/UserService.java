package com.tinyhappytrip.user.service;

import com.tinyhappytrip.security.jwt.JwtToken;
import com.tinyhappytrip.user.dto.UserRequest;
import com.tinyhappytrip.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    JwtToken login(UserRequest.Login login);

    int join(UserRequest.Join join);

    boolean validate(String type, String value);

    int editUserInfo(UserRequest.Edit edit);

    int withdrawal();

    UserResponse.UserInfo getUser(Long userId);

    int addFollow(Long followerId);

    int removeFollow(Long followerId);

    List<Long> getFollowList(String type, Long userId);
}
