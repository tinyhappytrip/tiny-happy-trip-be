package com.tinyhappytrip.playlist.dto;

import com.tinyhappytrip.playlist.domain.Playlist;
import com.tinyhappytrip.playlist.domain.PlaylistComment;
import com.tinyhappytrip.playlist.domain.enums.Scope;
import com.tinyhappytrip.story.domain.Story;
import com.tinyhappytrip.story.dto.StoryResponseDto;
import com.tinyhappytrip.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PlaylistResponseDto {
    @Getter
    @Setter
    @Builder
    public static class PlaylistInfo {
        private Long userId;
        private Long playlistId;
        private String createdAt;
        private String title;
        private String description;
        private Scope scope;
        private List<String> image; // 아이템 여러 개 중 첫번째의 사진 get(0)
        private List<PlaylistItem> playlistItems;
        private List<Comment> comments;
        private List<String> hashtags;
        private String musicKeyword;
        private int likeCount;
        private String nickname;
        private String profileImagePath;

        public static PlaylistInfo from(User user, Playlist playlist) {
            return PlaylistInfo.builder()
                    .playlistId(playlist.getPlaylistId())
                    .userId(playlist.getUserId())
                    .title(playlist.getTitle())
                    .description(playlist.getDescription())
                    .scope(playlist.getScope())
                    .createdAt(playlist.getCreatedAt())
                    .musicKeyword(playlist.getMusicKeyword())
                    .nickname(user.getNickname())
                    .profileImagePath(user.getProfileImagePath())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Comment {
        private Long userId;
        private Long playlistCommentId;
        private Long playlistId;
        private String content;
        private String createdAt;
        private String nickname;
        private String profileImagePath;

        public static Comment from(User user, PlaylistComment playlistComment) {
            return Comment.builder()
                    .userId(user.getUserId())
                    .playlistCommentId(playlistComment.getPlaylistCommentId())
                    .playlistId(playlistComment.getPlaylistId())
                    .content(playlistComment.getContent())
                    .createdAt(playlistComment.getCreatedAt())
                    .nickname(user.getNickname())
                    .profileImagePath(user.getProfileImagePath())
                    .build();
        }
    }


    @Getter
    @Setter
    @Builder
    public static class PlaylistItem {
        private Long storyId;
        private String imagePath;
        private String createdAt;
        private String location;

        public static PlaylistItem from(Story story, String imagePath) {
            return PlaylistItem.builder()
                    .storyId(story.getStoryId())
                    .imagePath(imagePath)
                    .createdAt(story.getCreatedAt())
                    .location(story.getLocation())
                    .build();
        }
    }
}