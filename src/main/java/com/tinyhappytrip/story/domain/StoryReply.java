package com.tinyhappytrip.story.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoryReply {
    private Long storyReplyId;
    private Long storyCommentId;
    private Long userId;
    private String content;
    private String createdAt;
}
