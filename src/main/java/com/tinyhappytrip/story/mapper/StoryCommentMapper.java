package com.tinyhappytrip.story.mapper;

import com.tinyhappytrip.story.domain.StoryComment;
import com.tinyhappytrip.story.domain.StoryReply;
import com.tinyhappytrip.story.dto.StoryRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryCommentMapper {
    int insert(Long storyId, String type, Long userId, StoryRequestDto.Comment comment);

    int delete(Long storyCommentId, Long userId);

    List<StoryComment> selectCommentByStoryId(Long storyId);

    List<StoryReply> selectReplyByStoryCommentId(Long storyCommentId);
}
