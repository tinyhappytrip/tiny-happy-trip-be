package com.tinyhappytrip.story.mapper;

import com.tinyhappytrip.story.dto.StoryRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryImageMapper {
    void insert(StoryRequestDto.Create create);

    List<String> selectAllByStoryId(Long storyId);
}
