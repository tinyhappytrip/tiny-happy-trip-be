package com.tinyhappytrip.story.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoryLikeMapper {
    int selectCountByStoryIdAndUserId(Long storyId, Long userId);

    int delete(Long storyId, Long userId);

    int insert(Long storyId, Long userId);

    int selectCountByStoryId(Long storyId);
}
