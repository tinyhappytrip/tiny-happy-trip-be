package com.tinyhappytrip.user.service;

import com.tinyhappytrip.security.jwt.JwtToken;
import com.tinyhappytrip.security.jwt.JwtTokenProvider;
import com.tinyhappytrip.security.util.SecurityUtil;
import com.tinyhappytrip.user.domain.User;
import com.tinyhappytrip.user.dto.UserRequest;
import com.tinyhappytrip.user.dto.UserResponse;
import com.tinyhappytrip.user.mapper.FollowMapper;
import com.tinyhappytrip.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final FollowMapper followMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public JwtToken login(UserRequest.LoginDto loginDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            return jwtTokenProvider.generateToken(authentication);
        } catch (AuthenticationException e) {
            return null;
        }
    }

    @Override
    public int join(UserRequest.JoinDto joinDto) {
        if (joinDto.getPassword() != null) {
            joinDto.setPassword(passwordEncoder.encode(joinDto.getPassword()));
        }
        return userMapper.insert(joinDto.toEntity());
    }

    @Override
    public boolean validate(String type, String value) {
        return userMapper.selectByTypeAndValue(type, value).isPresent();
    }

    @Override
    public int editUserInfo(UserRequest.EditDto editDto) {
        editDto.setUserId(SecurityUtil.getCurrentUserId());
        if (editDto.getPassword() != null) {
            editDto.setPassword(passwordEncoder.encode(editDto.getPassword()));
        }
        return userMapper.update(editDto.toEntity());
    }

    @Override
    public int withdrawal() {
        return userMapper.delete(SecurityUtil.getCurrentUserId());
    }

    @Override
    public UserResponse.UserDto getUser(Long userId) {
        return userMapper.selectByUserId(userId)
                .map(user -> UserResponse.UserDto.toUserDto(
                        user,
                        followMapper.selectFollowCountByUserId("follower", userId),
                        followMapper.selectFollowCountByUserId("followee", userId)))
                .orElse(null);
    }

    @Override
    public void resetPassword(String email) {
        String password = generateRandomPassword(15, passwordEncoder);
        userMapper.updatePassword(email, password);
    }

    @Override
    public int addFollow(Long followerId) {
        return followMapper.insert(SecurityUtil.getCurrentUserId(), followerId);
    }

    @Override
    public int removeFollow(Long followerId) {
        return followMapper.delete(SecurityUtil.getCurrentUserId(), followerId);
    }

    @Override
    public List<UserResponse.FollowUserDto> getFollowList(String type, Long userId) {
        return followMapper.selectAllByUserId(type, userId).stream()
                .map(followId -> userMapper.selectByUserId(followId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(UserResponse.FollowUserDto::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void uploadUserImage(String basePath, MultipartFile userImageFile) throws IOException {
        Long userId = SecurityUtil.getCurrentUserId();
        deletePreviousImage(userId);
        String storedFileName = UUID.randomUUID().toString() + "_" + userImageFile.getOriginalFilename();
        String userImage = Paths.get(basePath, storedFileName).toString();
        File storedFile = new File(userImage);
        userMapper.updateUserImage(userId, userImage);
        userImageFile.transferTo(storedFile);
    }

    private void deletePreviousImage(Long userId) {
        User user = userMapper.selectByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        String userImage = user.getUserImage();
        if (!userImage.equals("C:\\tinyhappytrip\\user\\default.jpg")) {
            File previousImageFile = new File(userImage);
            if (previousImageFile.exists()) {
                previousImageFile.delete();
            }
        }
    }

    private static String generateRandomPassword(int length, PasswordEncoder passwordEncoder) {
        StringBuilder password = new StringBuilder();
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * allowedChars.length());
            password.append(allowedChars.charAt(randomIndex));
        }
        return passwordEncoder.encode(password.toString());
    }
}