package com.tinyhappytrip.playlist.mapper;

import com.tinyhappytrip.playlist.domain.PlaylistComment;
import com.tinyhappytrip.playlist.dto.PlaylistResponseDto;
import org.apache.ibatis.annotations.Mapper;
import com.tinyhappytrip.playlist.dto.PlaylistRequestDto;
import java.util.List;

@Mapper
public interface PlaylistCommentMapper {
    int insert(Long playlistId, Long userId, String content);

    int delete(Long playlistCommentId, Long userId);

    List<PlaylistComment> selectCommentByPlaylistId(Long playlistId);
}
