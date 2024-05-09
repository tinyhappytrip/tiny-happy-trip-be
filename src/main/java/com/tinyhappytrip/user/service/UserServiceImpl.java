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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final FollowMapper followMapper;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtToken login(UserRequest.Login login) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authentication);
    }

    public int join(UserRequest.Join join) {
        join.setPassword(passwordEncoder.encode(join.getPassword()));
        return userMapper.insert(join);
    }

    public boolean validate(String type, String value) {
        return userMapper.selectByType(type, value).isPresent();
    }

    public int editUserInfo(UserRequest.Edit edit) {
        if (edit.getPassword() != null) {
            edit.setPassword(passwordEncoder.encode(edit.getPassword()));
        }
        return userMapper.update(edit);
    }

    public int withdrawal() {
        return userMapper.delete(SecurityUtil.getCurrentUserId());
    }

    public UserResponse.UserInfo getUser(Long userId) {
        return userMapper.selectByUserId(userId)
                .map(user -> {
                    UserResponse.UserInfo userInfo = UserResponse.UserInfo.from(user);
                    userInfo.setFollowerCount(followMapper.selectFollowCountByUserId("follower", userId));
                    userInfo.setFollowingCount(followMapper.selectFollowCountByUserId("followee", userId));
                    return userInfo;
                })
                .orElse(null);
    }

    public int addFollow(Long followerId) {
        return followMapper.insert(SecurityUtil.getCurrentUserId(), followerId);
    }

    public int removeFollow(Long followerId) {
        return followMapper.delete(SecurityUtil.getCurrentUserId(), followerId);
    }

    public List<Long> getFollowList(String type, Long userId) {
        return followMapper.selectAllByUserId(type, userId);
    }
}