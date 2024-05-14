package com.tinyhappytrip.playlist.mapper;

import com.tinyhappytrip.playlist.domain.Playlist;
import com.tinyhappytrip.playlist.dto.PlaylistRequestDto;
import com.tinyhappytrip.playlist.dto.PlaylistResponseDto;
import com.tinyhappytrip.story.dto.StoryRequestDto;
import com.tinyhappytrip.story.dto.StoryResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaylistMapper {
    int insert(Playlist playlist);

    int delete(Long userId, Long playlistId);

    int update(Long userId, Long playlistId, Playlist playlist);
    Playlist selectPlaylistByPlaylistId(Long playlistId);
    List<Playlist> selectUserPlaylist(Long userId);
    List<Playlist> selectThreePlaylist();
}