package com.tinyhappytrip.story.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryHashtagMapper {
    void insert(Long storyId, List<String> hashtags);

    List<String> selectHashtagByStoryId(Long storyId);
}
