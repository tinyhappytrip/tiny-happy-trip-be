package com.tinyhappytrip.story.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryLikeMapper {
    int selectCountByStoryIdAndUserId(Long storyId, Long userId);

    int delete(Long storyId, Long userId);

    int insert(Long storyId, Long userId);

    Long selectCountByStoryId(Long storyId);

    List<Long> selectStoryIdByUserId(Long userId);
}
