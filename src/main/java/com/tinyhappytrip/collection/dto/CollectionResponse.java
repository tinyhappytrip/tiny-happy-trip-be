package com.tinyhappytrip.collection.dto;

import com.tinyhappytrip.collection.domain.Collection;
import com.tinyhappytrip.collection.domain.CollectionComment;
import com.tinyhappytrip.collection.domain.enums.Scope;
import com.tinyhappytrip.story.domain.Story;
import com.tinyhappytrip.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CollectionResponse {
    @Getter
    @Setter
    @Builder
    public static class CollectionInfo {
        private Long userId;
        private Long collectionId;
        private String createdAt;
        private String title;
        private String description;
        private Scope scope;
        private List<String> image; // 아이템 여러 개 중 첫번째의 사진 get(0)
        private List<collectionItem> collectionItems;
        private List<Comment> comments;
        private List<String> hashtags;
        private String musicKeyword;
        private int likeCount;
        private String nickname;
        private String profileImagePath;

        public static CollectionInfo from(User user, Collection collection) {
            return CollectionInfo.builder()
                    .collectionId(collection.getCollectionId())
                    .userId(collection.getUserId())
                    .title(collection.getTitle())
                    .description(collection.getDescription())
                    .scope(collection.getScope())
                    .createdAt(collection.getCreatedAt())
                    .musicKeyword(collection.getMusicKeyword())
                    .nickname(user.getNickname())
                    .profileImagePath(user.getUserImage())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Comment {
        private Long userId;
        private Long collectionCommentId;
        private Long collectionId;
        private String content;
        private String createdAt;
        private String nickname;
        private String profileImagePath;

        public static Comment from(User user, CollectionComment collectionComment) {
            return Comment.builder()
                    .userId(user.getUserId())
                    .collectionCommentId(collectionComment.getCollectionCommentId())
                    .collectionId(collectionComment.getCollectionId())
                    .content(collectionComment.getContent())
                    .createdAt(collectionComment.getCreatedAt())
                    .nickname(user.getNickname())
                    .profileImagePath(user.getUserImage())
                    .build();
        }
    }


    @Getter
    @Setter
    @Builder
    public static class collectionItem {
        private Long storyId;
        private String imagePath;
        private String createdAt;
        private String placeName;
        private Long placeId;
        private String roadAddressName;
        private double latitude;
        private double longitude;

        public static collectionItem from(Story story, String imagePath) {
            return collectionItem.builder()
                    .storyId(story.getStoryId())
                    .imagePath(imagePath)
                    .createdAt(story.getCreatedAt())
                    .placeName(story.getPlaceName())
                    .placeId(story.getPlaceId())
                    .roadAddressName(story.getRoadAddressName())
                    .latitude(story.getLatitude())
                    .longitude(story.getLongitude())
                    .build();
        }
    }
}