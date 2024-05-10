package com.tinyhappytrip.story.mapper;

import com.tinyhappytrip.story.dto.FileInfoDto;
import com.tinyhappytrip.story.dto.StoryRequestDto;
import com.tinyhappytrip.story.dto.StoryResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryMapper {
    int insertStoryByStory(StoryRequestDto.CreateStory story);
    int insertImageByStoryImage(StoryRequestDto.CreateImage storyImage);
    int insertHashtagByStoryHashtag(StoryRequestDto.CreateHashtag storyHashtag);
    int insertTagByStoryTag(StoryRequestDto.CreateTag storyTag);

    int deleteStoryByStoryId(int storyId);
    int deleteImagesByStoryId(int storyId);
    int deleteHashtagsByStoryId(int storyId);
    int deleteTagsByStoryId(int storyId);

    int updateStoryByStory(int storyId, StoryRequestDto.Update story);

    int insertStoryLikeByStoryIdAndUserId(int storyId, int userId);
    int deleteStoryLikeByStoryIdAndUserId(int storyId, int userId);

    StoryResponseDto.SelectStory selectStoryByStoryId(int storyId);
    List<FileInfoDto>  selectStoryImagesByStoryId(int storyId);
    List<String> selectStoryHashtagsByStoryId(int storyId);
    List<String> selectStoryTagsByStoryId(int storyId);

    List<Integer> selectAllStoriesIdsByUserId(int userId);

    int selectStoryLikeCountByStoryId(int storyId);

    List<Integer> selectMyStoriesIdsByUserId(int userId);
}
