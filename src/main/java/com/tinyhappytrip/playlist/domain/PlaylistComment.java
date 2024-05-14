package com.tinyhappytrip.playlist.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistComment {
    private Long playlistCommentId;
    private Long userId;
    private Long playlistId;
    private String content;
    private String createdAt;
}
