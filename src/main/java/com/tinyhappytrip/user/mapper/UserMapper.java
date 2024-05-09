package com.tinyhappytrip.user.mapper;

import com.tinyhappytrip.user.domain.User;
import com.tinyhappytrip.user.dto.UserRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> selectByEmail(String email);

    int insert(UserRequest.Join join);

    Optional<User> selectByType(String type, String value);

    int update(UserRequest.Edit edit);

    int delete(Long userId);

    int selectFollowerCountByUserId(Long followerId);

    int selectFollowingCountByUserId(Long followingId);

    Optional<User> selectByUserId(Long userId);

    List<Long> selectAllFollowerByUserId(Long userId);

    List<Long> selectAllFollowingByUserId(Long userId);
}
