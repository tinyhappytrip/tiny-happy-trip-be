package com.tinyhappytrip.story.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryImageMapper {
    void insert(Long storyId, List<String> storyImages);

    List<String> selectAllByStoryId(Long storyId);
}
