package com.tinyhappytrip.playlist.service;//package com.tinyhappytrip.playlist.service;

import com.tinyhappytrip.playlist.domain.Playlist;
import com.tinyhappytrip.playlist.dto.PlaylistRequestDto;
import com.tinyhappytrip.playlist.dto.PlaylistResponseDto;

import java.util.List;

public interface PlaylistService {
    int createPlaylist(String basePath, PlaylistRequestDto.Create create);
    int deletePlaylist(Long playlistId);
    int updatePlaylist(String basePath, Long playlistId, PlaylistRequestDto.Update update);
    int setPlaylistLike(Long playlistId);
    PlaylistResponseDto.PlaylistInfo getPlaylistByPlaylistId(Long playlistId);
    List<PlaylistResponseDto.PlaylistInfo> getUserPlaylist();
    List<PlaylistResponseDto.PlaylistInfo> getUserLikePlaylist();
    List<PlaylistResponseDto.PlaylistInfo> getTopThreePlaylist();

    // 댓글
    int addComment(Long playlistId, PlaylistRequestDto.Comment comment);
    int deleteComment(Long playlistCommentId);
}

