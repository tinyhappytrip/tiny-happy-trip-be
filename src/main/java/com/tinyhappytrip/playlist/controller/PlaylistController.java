package com.tinyhappytrip.playlist.controller;//package com.tinyhappytrip.playlist.controller;

import com.tinyhappytrip.playlist.dto.PlaylistRequest;
import com.tinyhappytrip.playlist.dto.PlaylistResponse;
import com.tinyhappytrip.playlist.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    // 플레이리스트 생성하기
    @PostMapping
    public ResponseEntity<Void> createPlaylist(@Value("${image.playlist}") String basePath, @ModelAttribute PlaylistRequest.CreateDto createDto) {
        return ResponseEntity.status(playlistService.createPlaylist(basePath, createDto) == 1 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 플레이리스트 삭제하기
    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Integer> deletePlaylist(@PathVariable Long playlistId) {
        return ResponseEntity.status(playlistService.deletePlaylist(playlistId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 플레이리스트 수정하기
    @PutMapping("/{playlistId}")
    public ResponseEntity<Integer> updatePlaylist(@Value("${image.playlist}") String basePath,
                                                  @PathVariable Long playlistId,
                                                  @ModelAttribute PlaylistRequest.Update update) {
        return ResponseEntity.status(playlistService.updatePlaylist(basePath, playlistId, update) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 플레이리스트 좋아요
    @PostMapping("/like/{playlistId}")
    public ResponseEntity<Integer> likePlaylist(@PathVariable Long playlistId) {
        return ResponseEntity.status(playlistService.setPlaylistLike(playlistId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistResponse.PlaylistInfo> getPlaylist(@PathVariable Long playlistId) {
        System.out.println("get");
        return ResponseEntity.ok(playlistService.getPlaylistByPlaylistId(playlistId));
    }

    // 내 플레이리스트
    @GetMapping("/user")
    public ResponseEntity<List<PlaylistResponse.PlaylistInfo>> getUserPlaylist() {
        System.out.println("내 플레이리스트");
        return ResponseEntity.ok(playlistService.getUserPlaylist());
    }

    // 추천 플레이리스트 (좋아요 top3)
    @GetMapping("/recommend")
    public ResponseEntity<List<PlaylistResponse.PlaylistInfo>> getTopThreePlaylist() {
        return ResponseEntity.ok(playlistService.getTopThreePlaylist());
    }

    // 내가 좋아요 누른 플레이리스트
    @GetMapping("/user-like")
    public ResponseEntity<List<PlaylistResponse.PlaylistInfo>> getUserLikePlaylist() {
        return ResponseEntity.ok(playlistService.getUserLikePlaylist());
    }

    // 댓글 달기
    @PostMapping("/comment/{playlistId}")
    public ResponseEntity<Void> addComment(@PathVariable Long playlistId, @RequestBody PlaylistRequest.Comment comment) {
        return ResponseEntity.status(playlistService.addComment(playlistId, comment) == 1 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{playlistCommentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long playlistCommentId) {
        return ResponseEntity.status(playlistService.deleteComment(playlistCommentId) == 1 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}