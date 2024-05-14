package com.tinyhappytrip.story.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoryComment {
    private Long storyCommentId;
    private Long userId;
    private Long storyId;
    private String content;
    private String createdAt;
    private List<StoryReply> storyReply;
}
