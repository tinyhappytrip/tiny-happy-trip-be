package com.tinyhappytrip.playlist.mapper;

import com.tinyhappytrip.playlist.domain.Playlist;
import com.tinyhappytrip.playlist.dto.PlaylistRequestDto;
import com.tinyhappytrip.story.dto.StoryRequestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlaylistItemMapper {
    int insert(Long playlistId, List<Long> playlistItems);
    int delete(Long playlistId);
    List<Long> selectPlaylistItems(Long playlistId);
}
