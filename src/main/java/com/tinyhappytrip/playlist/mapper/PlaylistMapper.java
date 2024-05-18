package com.tinyhappytrip.playlist.mapper;

import com.tinyhappytrip.playlist.domain.Playlist;
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