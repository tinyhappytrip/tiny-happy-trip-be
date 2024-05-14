package com.tinyhappytrip.story.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryTagMapper {
    void insert(Long storyId, List<Integer> tags);

    List<String> selectUserIdByStoryId(Long storyId);
}
