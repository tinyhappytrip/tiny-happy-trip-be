package com.tinyhappytrip.user.mapper;

import com.tinyhappytrip.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> selectByEmail(String email);

    int insert(User user);

    Optional<User> selectByTypeAndValue(String type, String value);

    int update(User user);

    int delete(Long userId);

    Optional<User> selectByUserId(Long userId);

    void updatePassword(String email, String password);

    void updateUserImage(Long userId, String userImage);
}
