package com.tinyhappytrip.user.service;

import com.tinyhappytrip.security.jwt.JwtToken;
import com.tinyhappytrip.user.dto.UserRequest;
import com.tinyhappytrip.user.dto.UserResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    JwtToken login(UserRequest.LoginDto loginDto);

    int join(UserRequest.JoinDto joinDto);

    boolean validate(String type, String value);

    int editUserInfo(UserRequest.EditDto editDto);

    int withdrawal();

    UserResponse.UserDto getUser(Long userId);

    int addFollow(Long followerId);

    int removeFollow(Long followerId);

    List<UserResponse.FollowUserDto> getFollowList(String type, Long userId);

    void resetPassword(String email);

    @Transactional
    void uploadUserImage(String basePath, MultipartFile userImageFile) throws IOException;
}
