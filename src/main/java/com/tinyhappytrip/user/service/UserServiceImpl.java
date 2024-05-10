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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
    public JwtToken login(UserRequest.Login login) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public int join(UserRequest.Join join) {
        join.setPassword(passwordEncoder.encode(join.getPassword()));
        return userMapper.insert(join);
    }

    @Override
    public boolean validate(String type, String value) {
        return userMapper.selectTypeByValue(type, value).isPresent();
    }

    @Override
    public int editUserInfo(UserRequest.Edit edit) {
        Long userId = SecurityUtil.getCurrentUserId();
        edit.setUserId(userId);
        if (edit.getPassword() != null) {
            edit.setPassword(passwordEncoder.encode(edit.getPassword()));
        }
        return userMapper.update(edit);
    }

    @Override
    public int withdrawal() {
        return userMapper.delete(SecurityUtil.getCurrentUserId());
    }

    @Override
    public UserResponse.UserInfo getUser(Long userId) {
        return userMapper.selectById(userId)
                .map(user -> {
                    UserResponse.UserInfo userInfo = UserResponse.UserInfo.from(user);
                    userInfo.setFollowerCount(followMapper.selectFollowCountByUserId("follower", userId));
                    userInfo.setFollowingCount(followMapper.selectFollowCountByUserId("followee", userId));
                    return userInfo;
                })
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
    public List<Long> getFollowList(String type, Long userId) {
        return followMapper.selectAllByUserId(type, userId);
    }

    @Override
    @Transactional
    public void uploadProfileImage(String basePath, MultipartFile profileImage) throws IOException {
        Long userId = SecurityUtil.getCurrentUserId();
        deletePreviousImage(userId);
        String originalFileName = profileImage.getOriginalFilename();
        String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
        String fullPath = Paths.get(basePath, storedFileName).toString();
        File storedFile = new File(fullPath);
        userMapper.updateProfileImage(userId, storedFileName, fullPath);
        profileImage.transferTo(storedFile);
    }

    private void deletePreviousImage(Long userId) {
        User user = userMapper.selectById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        String profileImagePath = user.getProfileImagePath();
        if (!profileImagePath.equals("default.jpg")) {
            File previousImageFile = new File(profileImagePath);
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