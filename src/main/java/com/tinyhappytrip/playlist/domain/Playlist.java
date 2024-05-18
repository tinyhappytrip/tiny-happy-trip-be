package com.tinyhappytrip.playlist.domain;

import com.tinyhappytrip.playlist.domain.enums.Scope;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Playlist {
    private Long playlistId;
    private Long userId;
    private String title;
    private String description;
    private Scope scope;
    private String createdAt;
    private String musicKeyword;
    private String playlistImage;
}
