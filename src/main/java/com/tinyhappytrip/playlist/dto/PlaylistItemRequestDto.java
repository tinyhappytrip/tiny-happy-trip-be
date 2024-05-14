package com.tinyhappytrip.playlist.dto;

import lombok.Getter;
import lombok.Setter;

public class PlaylistItemRequestDto {
    @Getter
    @Setter
    public static class PlaylistItem {
        private int playlistItemId;
        private int playlistId;
        private int storyId;
    }
}
