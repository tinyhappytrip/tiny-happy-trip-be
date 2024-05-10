package com.tinyhappytrip.user.service;

import com.tinyhappytrip.security.jwt.JwtToken;
import com.tinyhappytrip.user.dto.UserRequest;
import com.tinyhappytrip.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    void resetPassword(String email);

    @Transactional
    void uploadProfileImage(String basePath, MultipartFile profileImage) throws IOException;
}
