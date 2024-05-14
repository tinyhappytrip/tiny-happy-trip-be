package com.tinyhappytrip.playlist.mapper;

import com.tinyhappytrip.playlist.domain.Playlist;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaylistLikeMapper {
    int selectCountByPlaylistIdAndUserId(Long playlistId, Long userId);
    int insert(Long playlistId, Long userId);
    int delete(Long playlistId, Long userId);
    int selectCountByPlaylistId(Long playlistId);
    List<Long> selectUserLikePlaylist(Long userId);
    List<Long> selectTopThreePlaylist();
}


