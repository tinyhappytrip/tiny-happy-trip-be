package com.tinyhappytrip.story.service;

import com.tinyhappytrip.story.dto.StoryRequest;
import com.tinyhappytrip.story.dto.StoryResponse;

import java.util.List;

public interface StoryService {
    int createStory(String basePath, StoryRequest.CreateDto createDto) throws Exception;

    int deleteStory(Long storyId);

    int updateStory(Long storyId, StoryRequest.UpdateDto updateDto);

    int setStoryLike(Long storyId);

    List<StoryResponse.StoryDetailDto> getAllUserStory(Long userId);

    List<StoryResponse.StoryDetailDto> getAllStory();

    StoryResponse.StoryDetailDto getStory(Long storyId);

    int addComment(Long storyId, String content);

    int addReply(Long storyCommentId, String content);

    int removeComment(Long storyCommentId);

    int removeReply(Long storyReplyId);

    int editComment(Long storyCommentId, String content);

    int editReply(Long storyReplyId, String content);

    List<StoryResponse.StoryOverviewDto> getAllLikeStory();
}
