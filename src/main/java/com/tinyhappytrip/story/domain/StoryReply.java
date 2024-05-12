package com.tinyhappytrip.story.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryReply {
    private Long storyCommentId;
    private Long storyReplyId;
    private Long userId;
    private Long storyId;
    private String content;
    private String createdAt;
}
