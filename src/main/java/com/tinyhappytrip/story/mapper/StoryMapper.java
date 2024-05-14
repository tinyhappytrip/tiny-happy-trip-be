package com.tinyhappytrip.story.mapper;

import com.tinyhappytrip.story.domain.Story;
import com.tinyhappytrip.story.dto.StoryRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryMapper {
    void insert(Long userId, StoryRequestDto.Create create);

    int delete(Long storyId, Long userId);

    int update(Long storyId, Long userId, StoryRequestDto.Update story);

    List<Story> selectAllByUserId(Long userId);

    List<Story> selectUserStoriesByUserId(Long currentUserId);

    Story selectByStoryId(Long storyId);
}
