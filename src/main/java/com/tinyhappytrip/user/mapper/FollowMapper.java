package com.tinyhappytrip.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    Long selectFollowCountByUserId(String type, Long userId);

    List<Long> selectAllByUserId(String type, Long userId);

    int selectFollow(Long followeeId, Long followerId);

    int insert(Long followeeId, Long followerId);

    int delete(Long followeeId, Long followerId);
}
