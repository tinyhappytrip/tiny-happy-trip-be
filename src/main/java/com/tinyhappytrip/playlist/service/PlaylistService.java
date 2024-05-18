package com.tinyhappytrip.playlist.service;//package com.tinyhappytrip.playlist.service;

import com.tinyhappytrip.playlist.dto.PlaylistRequest;
import com.tinyhappytrip.playlist.dto.PlaylistResponse;

import java.util.List;

public interface PlaylistService {
    int createPlaylist(String basePath, PlaylistRequest.CreateDto createDto);

    int deletePlaylist(Long playlistId);

    int updatePlaylist(String basePath, Long playlistId, PlaylistRequest.Update update);

    int setPlaylistLike(Long playlistId);

    PlaylistResponse.PlaylistInfo getPlaylistByPlaylistId(Long playlistId);

    List<PlaylistResponse.PlaylistInfo> getUserPlaylist();

    List<PlaylistResponse.PlaylistInfo> getUserLikePlaylist();

    List<PlaylistResponse.PlaylistInfo> getTopThreePlaylist();

    // 댓글
    int addComment(Long playlistId, PlaylistRequest.Comment comment);

    int deleteComment(Long playlistCommentId);
}

