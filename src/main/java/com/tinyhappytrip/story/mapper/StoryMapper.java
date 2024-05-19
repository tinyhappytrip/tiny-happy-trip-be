package com.tinyhappytrip.story.mapper;

import com.tinyhappytrip.story.domain.Story;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryMapper {
    void insert(Long userId, Story story);

    int delete(Long storyId, Long userId);

    int update(Long storyId, Long userId, Story story);

    List<Story> selectAllByUserId(Long userId);

    List<Story> selectUserStoriesByUserId(Long currentUserId);

    List<Story> selectStoriesBySearchKeyword(String searchKeyword);
    Story selectByStoryId(Long storyId);
}
