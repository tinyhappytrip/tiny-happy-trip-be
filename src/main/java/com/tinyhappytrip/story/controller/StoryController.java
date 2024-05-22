package com.tinyhappytrip.story.controller;

import com.tinyhappytrip.story.dto.StoryRequest;
import com.tinyhappytrip.story.dto.StoryResponse;
import com.tinyhappytrip.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    // 스토리 생성하기
    @PostMapping
    public ResponseEntity<Void> createStory(@Value("${image.story}") String basePath, @ModelAttribute StoryRequest.CreateDto createDto) throws Exception {
        return ResponseEntity.status(storyService.createStory(basePath, createDto) == 1 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 스토리 삭제
    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long storyId) {
        return ResponseEntity.status(storyService.deleteStory(storyId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 스토리 수정
    @PatchMapping("/{storyId}")
    public ResponseEntity<Void> updateStory(@PathVariable Long storyId, @RequestBody StoryRequest.UpdateDto updateDto) {
        return ResponseEntity.status(storyService.updateStory(storyId, updateDto) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 스토리 좋아요 추가 / 삭제
    @PostMapping("/like/{storyId}")
    public ResponseEntity<Void> setStoryLike(@PathVariable Long storyId) {
        return ResponseEntity.status(storyService.setStoryLike(storyId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 유저 스토리 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<StoryResponse.StoryDetailDto>> getAllUserStory(@PathVariable Long userId) {
        return ResponseEntity.ok(storyService.getAllUserStory(userId));
    }

    // 전체 스토리 목록
    @GetMapping
    public ResponseEntity<List<StoryResponse.StoryDetailDto>> getAllStory() {
        System.out.println(storyService.getAllStory());
        return ResponseEntity.ok(storyService.getAllStory());
    }

    // 유저가 좋아요 누른 스토리 목록
    @GetMapping("/like")
    public ResponseEntity<List<StoryResponse.StoryDetailDto>> getAllLikeStory() {
        return ResponseEntity.ok(storyService.getAllLikeStory());
    }

    // 검색 스토리 목록
    @GetMapping("/search/{searchKeyword}")
    public ResponseEntity<List<StoryResponse.StoryDetailDto>> getAllSearchStory(@PathVariable String searchKeyword) {
        System.out.println("search!!: " + searchKeyword);
        System.out.println(storyService.getAllSearchStory(searchKeyword));
        return ResponseEntity.ok(storyService.getAllSearchStory(searchKeyword));
    }

    // 스토리 상세
    @GetMapping("/detail/{storyId}")
    public ResponseEntity<StoryResponse.StoryDetailDto> getStory(@PathVariable Long storyId) {
        return ResponseEntity.ok(storyService.getStory(storyId));
    }

    // 댓글 추가
    @PostMapping("/comments/{storyId}")
    public ResponseEntity<Void> addComment(@PathVariable Long storyId, @RequestBody String content) {
        String newContent = content.substring(1, content.length() - 1);
        return ResponseEntity.status(storyService.addComment(storyId, newContent) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 답글 추가
    @PostMapping("/replies/{storyCommentId}")
    public ResponseEntity<Void> addReply(@PathVariable Long storyCommentId, @RequestBody String content) {
        String newContent = content.substring(1, content.length() - 1);
        return ResponseEntity.status(storyService.addReply(storyCommentId, newContent) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{storyCommentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long storyCommentId) {
        return ResponseEntity.status(storyService.removeComment(storyCommentId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 답글 삭제
    @DeleteMapping("/replies/{storyReplyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long storyReplyId) {
        return ResponseEntity.status(storyService.removeReply(storyReplyId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 댓글 수정
    @PatchMapping("/comments/{storyCommentId}")
    public ResponseEntity<Void> editComment(@PathVariable Long storyCommentId, @RequestBody String content) {
        return ResponseEntity.status(storyService.editComment(storyCommentId, content) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 답글 수정
    @PatchMapping("/replies/{storyReplyId}")
    public ResponseEntity<Void> editReply(@PathVariable Long storyReplyId, @RequestBody String content) {
        return ResponseEntity.status(storyService.editReply(storyReplyId, content) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
