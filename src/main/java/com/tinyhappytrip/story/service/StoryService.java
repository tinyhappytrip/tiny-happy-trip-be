package com.tinyhappytrip.story.service;

import com.tinyhappytrip.story.dto.StoryRequestDto;
import com.tinyhappytrip.story.dto.StoryResponseDto;

import java.io.IOException;
import java.util.List;

public interface StoryService {
    // 스토리 생성하기
    public int createStory(StoryRequestDto.Create story, int userId) throws IOException;

    // 스토리 삭제
    public int deleteStory(int storyId);

    // 스토리 수정
    public int updateStory(int storyId, StoryRequestDto.Update story);

    // 스토리 좋아요 (스토리 수정)
    public int likeStory(int storyId, int userId);
    public int notlikeStory(int storyId, int userId);

    // 전체 스토리 조회
    public List<StoryResponseDto.Story> stories(int userId);

    // 특정 스토리 조회
    public StoryResponseDto.Story story(int storyId);

    public List<StoryResponseDto.Story> mystories(int userId);
}
