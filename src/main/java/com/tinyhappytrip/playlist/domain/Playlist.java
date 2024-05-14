package com.tinyhappytrip.playlist.domain;

import com.tinyhappytrip.playlist.domain.enums.Scope;
import com.tinyhappytrip.story.domain.Story;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
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
    private String imagePath;

    public Playlist(Long userId, String title, String description, Scope scope, String musicKeyword, String imagePath) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.scope = scope;
        this.musicKeyword = musicKeyword;
        this.imagePath = imagePath;
    }
}
