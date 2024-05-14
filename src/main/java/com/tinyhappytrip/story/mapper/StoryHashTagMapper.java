package com.tinyhappytrip.story.mapper;

import com.tinyhappytrip.story.dto.StoryRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryHashTagMapper {
    void insert(StoryRequestDto.Create create);

    List<String> selectHashTagByStoryId(Long storyId);
}
