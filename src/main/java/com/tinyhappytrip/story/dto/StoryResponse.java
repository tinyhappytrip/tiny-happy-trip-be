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
import java.util.Objects;

public class StoryResponse {

    @Getter
    @Setter
    @Builder
    public static class StoryOverviewDto {
        private Long storyId;
        private String createdAt;
        private String content;
        private String weather;
        private String emotion;
        private String placeName;
        private Long placeId;
        private String roadAddressName;
        private List<String> hashtags;
        private List<String> images;
        private Long likeCount;
        private boolean isLike;
        private Long userId;
        private String nickname;
        private String userImage;

        public static StoryOverviewDto toResponseDto(Story story, List<String> hashtags, List<String> images, Long likeCount, boolean isLike, User user) {
            return StoryOverviewDto.builder()
                    .storyId(story.getStoryId())
                    .userId(story.getUserId())
                    .createdAt(story.getCreatedAt())
                    .content(story.getContent())
                    .weather(story.getWeather())
                    .emotion(story.getEmotion())
                    .placeName(story.getPlaceName())
                    .placeId(story.getPlaceId())
                    .roadAddressName(story.getRoadAddressName())
                    .hashtags(hashtags)
                    .images(images)
                    .likeCount(likeCount)
                    .isLike(isLike)
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .userImage(user.getUserImage())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class StoryDetailDto {
        private Long storyId;
        private String createdAt;
        private String content;
        private String weather;
        private String emotion;
        private String placeName;
        private Long placeId;
        private String roadAddressName;
        private Scope scope;
        private double latitude;
        private double longitude;
        private List<String> hashtags;
        private List<String> images;
        private List<StoryCommentDto> storyComments;
        private Long likeCount;
        private boolean isLike;
        private Long userId;
        private String nickname;
        private String userImage;

        public static StoryDetailDto toResponseDto(Story story, User user, List<String> hashtags, List<String> images, List<StoryCommentDto> storyComments, Long likeCount, boolean isLike) {
            return StoryDetailDto.builder()
                    .storyId(story.getStoryId())
                    .userId(story.getUserId())
                    .createdAt(story.getCreatedAt())
                    .content(story.getContent())
                    .weather(story.getWeather())
                    .emotion(story.getEmotion())
                    .placeName(story.getPlaceName())
                    .roadAddressName(story.getRoadAddressName())
                    .placeId(story.getPlaceId())
                    .scope(story.getScope())
                    .latitude(story.getLatitude())
                    .longitude(story.getLongitude())
                    .hashtags(hashtags)
                    .images(images)
                    .storyComments(storyComments)
                    .likeCount(likeCount)
                    .isLike(isLike)
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .userImage(user.getUserImage())
                    .build();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StoryDetailDto that = (StoryDetailDto) o;
            return Objects.equals(storyId, that.storyId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(storyId);
        }

    }

    @Getter
    @Setter
    @Builder
    public static class StoryCommentDto {
        private Long storyCommentId;
        private String content;
        private String createdAt;
        private Long userId;
        private String nickname;
        private String userImage;
        private List<StoryReplyDto> replies;

        public static StoryCommentDto toResponseDto(User user, StoryComment storyComment, List<StoryReplyDto> replies) {
            return StoryCommentDto.builder()
                    .storyCommentId(storyComment.getStoryCommentId())
                    .content(storyComment.getContent())
                    .createdAt(storyComment.getCreatedAt())
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .userImage(user.getUserImage())
                    .replies(replies)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class StoryReplyDto {
        private Long storyReplyId;
        private String content;
        private String createdAt;
        private Long userId;
        private String nickname;
        private String userImage;

        public static StoryReplyDto toResponseDto(User user, StoryReply storyReply) {
            return StoryReplyDto.builder()
                    .storyReplyId(storyReply.getStoryReplyId())
                    .content(storyReply.getContent())
                    .createdAt(storyReply.getCreatedAt())
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .userImage(user.getUserImage())
                    .build();
        }
    }
}
