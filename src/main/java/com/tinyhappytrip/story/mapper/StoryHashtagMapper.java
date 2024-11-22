package com.tinyhappytrip.story.mapper;

import com.tinyhappytrip.story.domain.Story;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryHashtagMapper {
    void insert(Long storyId, List<String> hashtags);
    List<Long> selectStoryIdsBySearchKeyword(String searchKeyword);
    List<String> selectHashtagByStoryId(Long storyId);
}
