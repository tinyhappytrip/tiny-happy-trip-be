package com.tinyhappytrip.story.mapper;

import com.tinyhappytrip.story.domain.StoryComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryCommentMapper {
    int insert(Long userId, Long storyId, String content);

    int delete(Long userId, Long storyCommentId);

    List<StoryComment> selectByStoryId(Long storyId);

    int update(Long userId, Long storyCommentId, String content);

}
