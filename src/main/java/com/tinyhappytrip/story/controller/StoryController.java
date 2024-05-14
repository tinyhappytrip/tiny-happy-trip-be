package com.tinyhappytrip.story.controller;

import com.tinyhappytrip.story.dto.StoryRequestDto;
import com.tinyhappytrip.story.dto.StoryResponseDto;
import com.tinyhappytrip.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/story")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    // 스토리 생성하기
    @PostMapping
    public ResponseEntity<Void> createStory(@Value("${image.story}") String basePath, @ModelAttribute StoryRequestDto.Create create) throws IOException {
        return ResponseEntity.status(storyService.createStory(basePath, create) == 1 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 스토리 삭제
    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long storyId) {
        return ResponseEntity.status(storyService.deleteStory(storyId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 스토리 수정
    @PatchMapping("/{storyId}")
    public ResponseEntity<Void> updateStory(@PathVariable Long storyId, @RequestBody StoryRequestDto.Update update) {
        return ResponseEntity.status(storyService.updateStory(storyId, update) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 스토리 좋아요 추가 / 삭제
    @PostMapping("/like/{storyId}")
    public ResponseEntity<Void> setStoryLike(@PathVariable Long storyId) {
        return ResponseEntity.status(storyService.setStoryLike(storyId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 유저 스토리 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<StoryResponseDto.StoryInfo>> getAllUserStory(@PathVariable Long userId) {
        return ResponseEntity.ok(storyService.getAllUserStory(userId));
    }

    // 전체 스토리 목록
    @GetMapping
    public ResponseEntity<List<StoryResponseDto.StoryInfo>> getAllStory() {
        return ResponseEntity.ok(storyService.getAllStory());
    }

    // 스토리 상세
    @GetMapping("/detail/{storyId}")
    public ResponseEntity<StoryResponseDto.StoryInfo> getStory(@PathVariable Long storyId) {
        return ResponseEntity.ok(storyService.getStory(storyId));
    }

    // 댓글 및 답글
    @PostMapping("/comment/{storyId}")
    public ResponseEntity<Void> addComment(@PathVariable Long storyId, @RequestParam String type, @RequestBody StoryRequestDto.Comment comment) {
        return ResponseEntity.status(storyService.addComment(storyId, type, comment) == 1 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{storyCommentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long storyCommentId) {
        return ResponseEntity.status(storyService.deleteComment(storyCommentId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
