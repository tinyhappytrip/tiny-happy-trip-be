package com.tinyhappytrip.story.service;

import com.tinyhappytrip.story.dto.StoryRequestDto;
import com.tinyhappytrip.story.dto.StoryResponseDto;

import java.util.List;

public interface StoryService {
    int createStory(String basePath, StoryRequestDto.Create create);

    int deleteStory(Long storyId);

    int updateStory(Long storyId, StoryRequestDto.Update update);

    int setStoryLike(Long storyId);

    List<StoryResponseDto.StoryInfo> getAllUserStory(Long storyId);

    List<StoryResponseDto.StoryInfo> getAllStory();

    StoryResponseDto.StoryInfo getStory(Long storyId);

    int addComment(Long storyId, String type, StoryRequestDto.Comment comment);

    int deleteComment(Long commentId);
}
