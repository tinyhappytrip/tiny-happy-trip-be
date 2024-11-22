package com.tinyhappytrip.story.mapper;

import com.tinyhappytrip.story.domain.StoryReply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryReplyMapper {
    int insert(Long userId, Long storyCommentId, String content);

    int delete(Long userId, Long storyReplyId);

    List<StoryReply> selectByStoryCommentId(Long storyCommentId);

    int update(Long userId, Long storyReplyId, String content);

}
