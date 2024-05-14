package com.tinyhappytrip.playlist.service;//package com.tinyhappytrip.playlist.service;

import com.tinyhappytrip.playlist.domain.Playlist;
import com.tinyhappytrip.playlist.domain.PlaylistComment;
import com.tinyhappytrip.playlist.domain.enums.Scope;
import com.tinyhappytrip.playlist.dto.PlaylistRequestDto;
import com.tinyhappytrip.playlist.dto.PlaylistResponseDto;
import com.tinyhappytrip.playlist.mapper.*;
import com.tinyhappytrip.security.util.SecurityUtil;
import com.tinyhappytrip.story.mapper.StoryImageMapper;
import com.tinyhappytrip.story.mapper.StoryMapper;
import com.tinyhappytrip.user.domain.User;
import com.tinyhappytrip.user.mapper.UserMapper;
import com.tinyhappytrip.playlist.domain.Playlist;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minidev.json.JSONUtil;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistCommentMapper playlistCommentMapper;
    private final PlaylistHashTagMapper playlistHashTagMapper;
    private final PlaylistItemMapper playlistItemMapper;
    private final PlaylistLikeMapper playlistLikeMapper;
    private final PlaylistMapper playlistMapper;
    private final StoryImageMapper storyImageMapper;
    private final StoryMapper storyMapper;
    private final UserMapper userMapper;

    @Override
    public int createPlaylist(String basePath, PlaylistRequestDto.Create create) {
        try {
            Long userId = SecurityUtil.getCurrentUserId();
            String fullPath = saveFile(basePath, create.getImage());
            Playlist playlist = new Playlist(
                    userId,
                    create.getTitle(),
                    create.getDescription(),
                    create.getScope(),
                    create.getMusicKeyword(),
                    fullPath
            );
            playlistMapper.insert(playlist);
            playlistHashTagMapper.insert(playlist.getPlaylistId(), create.getHashtags());
            playlistItemMapper.insert(playlist.getPlaylistId(), create.getPlaylistItems());
             return 1;
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deletePlaylist(Long playlistId) {
        return playlistMapper.delete(SecurityUtil.getCurrentUserId(), playlistId);
    }

    @Override
    public int updatePlaylist(String basePath, Long playlistId, PlaylistRequestDto.Update update) {
        Long userId = SecurityUtil.getCurrentUserId();
        try {

            // UpdateDTO -> Domain
             Playlist playlist = new Playlist(
                     userId,
                     update.getTitle(),
                     update.getDescription(),
                     update.getScope(),
                     update.getMusicKeyword(),
                     saveFile(basePath, update.getImage())
             );
             System.out.println(playlistMapper.update(userId, playlistId, playlist));

             // 관련 PlaylistItem 삭제 및 삽입
             System.out.println(playlistItemMapper.delete(playlistId));
             System.out.println(playlistItemMapper.insert(playlistId, update.getPlaylistItems()));

             // 관련 PlaylistHashTag 삭제 및 삽입
            System.out.println(playlistHashTagMapper.delete(playlistId));
            System.out.println(playlistHashTagMapper.insert(playlistId, update.getHashtags()));

            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int setPlaylistLike(Long playlistId) {
        Long userId = SecurityUtil.getCurrentUserId();
        // 좋아요가 눌려있다면,
        if (playlistLikeMapper.selectCountByPlaylistIdAndUserId(playlistId, userId) == 1) {
            return playlistLikeMapper.delete(playlistId, userId);
        }
        return playlistLikeMapper.insert(playlistId, userId);
    }

    @Override
    public List<PlaylistResponseDto.PlaylistInfo> getUserPlaylist() {
        List<PlaylistResponseDto.PlaylistInfo> playlists = new ArrayList<>();
        Long userId = SecurityUtil.getCurrentUserId();
        for (Playlist playlist: playlistMapper.selectUserPlaylist(userId)) {
            PlaylistResponseDto.PlaylistInfo playlistInfo = makePlayListInfo(playlist);
            playlists.add(playlistInfo);
        }
        return playlists;
    }

    @Override
    public List<PlaylistResponseDto.PlaylistInfo> getUserLikePlaylist() {
        List<PlaylistResponseDto.PlaylistInfo> playlists = new ArrayList<>();
        Long userId = SecurityUtil.getCurrentUserId();
        for (Long playlistId: playlistLikeMapper.selectUserLikePlaylist(userId)) {
            PlaylistResponseDto.PlaylistInfo playlistInfo = makePlayListInfo(playlistMapper.selectPlaylistByPlaylistId(playlistId));
            playlists.add(playlistInfo);
        }
        return playlists;
    }

    @Override
    public List<PlaylistResponseDto.PlaylistInfo> getTopThreePlaylist() {
        List<PlaylistResponseDto.PlaylistInfo> playlists = new ArrayList<>();
        for (Long playlistId: playlistLikeMapper.selectTopThreePlaylist()) {
            PlaylistResponseDto.PlaylistInfo playlistInfo = makePlayListInfo(playlistMapper.selectPlaylistByPlaylistId(playlistId));
            playlists.add(playlistInfo);
        }

        if (playlists.size() == 0) {
            for (Playlist playlist: playlistMapper.selectThreePlaylist()) {
                playlists.add(makePlayListInfo(playlist));
            }
        }
        return playlists;
    }

    @Override
    public PlaylistResponseDto.PlaylistInfo getPlaylistByPlaylistId(Long playlistId) {
        return makePlayListInfo(playlistMapper.selectPlaylistByPlaylistId(playlistId));
    }

    @Override
    public int addComment(Long playlistId, PlaylistRequestDto.Comment comment) {
        return playlistCommentMapper.insert(playlistId, SecurityUtil.getCurrentUserId(), comment.getContent());
    }

    @Override
    public int deleteComment(Long playlistCommentId) {
        return playlistCommentMapper.delete(playlistCommentId, SecurityUtil.getCurrentUserId());
    }

    private String saveFile(String basePath, MultipartFile file) throws IOException {
        String yyyyMm = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String uploadPath = basePath + File.separator + yyyyMm;
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String originalFileName = file.getOriginalFilename();
        String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
        String fullPath = uploadPath + File.separator + storedFileName;
        File dest = new File(fullPath);
        file.transferTo(dest); // 파일 저장
        return fullPath;
    }

    private PlaylistResponseDto.PlaylistInfo makePlayListInfo(Playlist playlist) {
        User user = userMapper.selectByUserId(playlist.getUserId()).get();
        PlaylistResponseDto.PlaylistInfo playlistInfo = PlaylistResponseDto.PlaylistInfo.from(user, playlist);
        Long playlistId = playlistInfo.getPlaylistId();
        List<PlaylistResponseDto.PlaylistItem> playlistItems = new ArrayList<>();

        // 플레이리스트 내 모든 스토리들을 PlaylistItem type으로 변경 (PlaylistItem 대표 사진은 story 사진들 중 가장 앞 사진)
        for (Long storyId : playlistItemMapper.selectPlaylistItems(playlistId)) {
            System.out.println("playlist" + " " + playlist);
            System.out.println(storyId);
            System.out.println(storyImageMapper.selectAllByStoryId(storyId));
            playlistItems.add(
                    PlaylistResponseDto.PlaylistItem.from(
                            storyMapper.selectByStoryId(storyId),
                            storyImageMapper.selectAllByStoryId(storyId).get(0)
                    )
            );
        }
        playlistInfo.setPlaylistItems(playlistItems);

        // 플레이리스트 대표 사진이 설정되지 않은 경우
        if (playlist.getImagePath() == null) {
            System.out.println("대표 사진이 설정되지 않은 경우! playlistId" + " " +  playlistId);
            System.out.println(playlistItemMapper.selectPlaylistItems(playlistId));
            playlistInfo.setImage(storyImageMapper.selectAllByStoryId(playlistItemMapper.selectPlaylistItems(playlistId).get(0)));
        } else {
            List<String> images = new ArrayList<>();
            images.add(playlist.getImagePath());
            playlistInfo.setImage(images);
        }

        // 플레이리스트 해시태그 세팅
        List<String> hashtags = playlistHashTagMapper.selectHashTagsByPlaylistId(playlistId);
        playlistInfo.setHashtags(hashtags);

        // 플레이리스트 댓글 세팅
        List<PlaylistResponseDto.Comment> comments = new ArrayList<>();
        for (PlaylistComment playlistComment : playlistCommentMapper.selectCommentByPlaylistId(playlistId)) {
            comments.add(PlaylistResponseDto.Comment.from(user, playlistComment));
        }
        playlistInfo.setComments(comments);

        // 플레이리스트 좋아요 수 세팅
        playlistInfo.setLikeCount(playlistLikeMapper.selectCountByPlaylistId(playlistId));
        return playlistInfo;
    }
}