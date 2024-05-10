package com.tinyhappytrip.user.mapper;

import com.tinyhappytrip.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findByEmail(String email);
}
