package com.tinyhappytrip.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    int selectFollowCountByUserId(String type, Long userId);

    List<Long> selectAllByUserId(String type, Long userId);

    int insert(Long followeeId, Long followerId);

    int delete(Long followeeId, Long followerId);
}
