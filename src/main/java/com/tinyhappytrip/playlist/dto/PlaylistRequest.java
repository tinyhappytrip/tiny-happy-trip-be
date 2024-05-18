package com.tinyhappytrip.playlist.dto;

import com.tinyhappytrip.playlist.domain.Playlist;
import com.tinyhappytrip.playlist.domain.enums.Scope;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PlaylistRequest {
    @Getter
    @Setter
    @Builder
    public static class CreateDto {
        private Long userId;
        private Long playlistId;
        private String title;
        private String description;
        private Scope scope;
        private List<Long> playlistItems;
        private String musicKeyword;
        private List<String> hashtags;
        private MultipartFile imageFile;

        public Playlist toEntity() {
            return Playlist.builder()
                    .userId(userId)
                    .playlistId(playlistId)
                    .title(title)
                    .description(description)
                    .scope(scope)
                    .musicKeyword(musicKeyword)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Update {
        private Long userId;
        private Long playlistId;
        private String title;
        private String description;
        private Scope scope;
        private List<Long> playlistItems;
        private String musicKeyword;
        private List<String> hashtags;
        private MultipartFile image;
    }

    @Getter
    @Setter
    public static class Comment {
        private Long playlistCommentId;
        private Long playlistId;
        private String content;
    }
}
