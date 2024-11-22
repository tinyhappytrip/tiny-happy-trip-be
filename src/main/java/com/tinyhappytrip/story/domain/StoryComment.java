package com.tinyhappytrip.story.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StoryComment {
    private Long storyCommentId;
    private Long userId;
    private Long storyId;
    private String content;
    private String createdAt;
    private List<StoryReply> replies;
}
