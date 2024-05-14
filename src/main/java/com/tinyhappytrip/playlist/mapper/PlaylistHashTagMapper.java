package com.tinyhappytrip.playlist.mapper;

import com.tinyhappytrip.playlist.dto.PlaylistRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaylistHashTagMapper {
    int insert(Long playlistId, List<String> hashtags);
    int delete(Long playlistId);
    List<String> selectHashTagsByPlaylistId(Long playlistId);
}
