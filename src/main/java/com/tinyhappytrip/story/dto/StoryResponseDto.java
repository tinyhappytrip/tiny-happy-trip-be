package com.tinyhappytrip.story.dto;

import com.tinyhappytrip.story.domain.Story;
import com.tinyhappytrip.story.domain.StoryComment;
import com.tinyhappytrip.story.domain.StoryReply;
import com.tinyhappytrip.story.domain.enums.Scope;
import com.tinyhappytrip.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class StoryResponseDto {
    @Getter
    @Setter
    @Builder
    public static class StoryInfo {
        private Long storyId;
        private Long userId;
        private String createdAt;
        private String content;
        private String weather;
        private String emotion;
        private String location;
        private Scope scope;
        private double latitude;
        private double longitude;
        private List<String> hashtags;
        private List<String> tags;
        private List<String> images;
        private List<Comment> comments;
        private int likeCount;

        public static StoryInfo from(Story story) {
            return StoryInfo.builder()
                    .storyId(story.getStoryId())
                    .userId(story.getUserId())
                    .createdAt(story.getCreatedAt())
                    .content(story.getContent())
                    .weather(story.getWeather())
                    .emotion(story.getEmotion())
                    .location(story.getLocation())
                    .scope(story.getScope())
                    .latitude(story.getLatitude())
                    .longitude(story.getLongitude())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Comment {
        private Long userId;
        private Long storyCommentId;
        private Long storyId;
        private String createdAt;
        private String nickname;
        private String profileImagePath;
        private String content;
        private List<Reply> replies;

        public static Comment from(User user, StoryComment storyComment, List<Reply> replies) {
            return Comment.builder()
                    .userId(user.getUserId())
                    .storyCommentId(storyComment.getStoryCommentId())
                    .storyId(storyComment.getStoryId())
                    .createdAt(storyComment.getCreatedAt())
                    .nickname(user.getNickname())
                    .profileImagePath(user.getProfileImagePath())
                    .content(storyComment.getContent())
                    .replies(replies)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Reply {
        private Long userId;
        private Long storyCommentId;
        private Long storyReplyId;
        private Long storyId;
        private String createdAt;
        private String nickname;
        private String profileImagePath;
        private String content;

        public static List<Reply> from(User user, List<StoryReply> storyReplies) {
            return storyReplies.stream()
                    .map(storyReply -> Reply.builder()
                            .userId(storyReply.getUserId())
                            .storyCommentId(storyReply.getStoryCommentId())
                            .storyId(storyReply.getStoryId())
                            .createdAt(storyReply.getCreatedAt())
                            .nickname(user.getNickname())
                            .profileImagePath(user.getProfileImagePath())
                            .content(storyReply.getContent())
                            .build())
                    .collect(Collectors.toList());
        }
    }
}
