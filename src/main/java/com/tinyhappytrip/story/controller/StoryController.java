package com.tinyhappytrip.story.controller;

import com.tinyhappytrip.story.dto.StoryRequestDto;
import com.tinyhappytrip.story.dto.StoryResponseDto;
import com.tinyhappytrip.story.service.StoryService;
import lombok.RequiredArgsConstructor;
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

    public int userId = 1234;
    public int userId2 = 5678;

    // 스토리 생성하기
    @PostMapping
    public ResponseEntity<Void> createStory(@RequestBody StoryRequestDto.Create story) throws IOException {
        int res = storyService.createStory(story, userId2);
        return ResponseEntity.status(res == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 스토리 삭제
    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable int storyId) {
        int res = storyService.deleteStory(storyId);
        return ResponseEntity.status(res == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 스토리 수정
    @PatchMapping("/{storyId}")
    public ResponseEntity<Void> updateStory(@PathVariable int storyId, @RequestBody StoryRequestDto.Update story) {
        int res = storyService.updateStory(storyId, story);
        return ResponseEntity.status(res == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 스토리 좋아요 삽입
    @PostMapping("/like/{storyId}")
    public ResponseEntity<Void> likeStory(@PathVariable int storyId) {
        int res = storyService.likeStory(storyId, userId2);
        return ResponseEntity.status(res == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 스토리 좋아요 취소
    @DeleteMapping("/notlike/{storyId}")
    public ResponseEntity<Void> notlikeStory(@PathVariable int storyId) {
        int res = storyService.notlikeStory(storyId, userId2);
        return ResponseEntity.status(res == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 전체 스토리 조회
    @GetMapping
    public List<StoryResponseDto.Story> stories() {
        return storyService.stories(userId2);
    }

    // 특정 스토리 조회
    @GetMapping("/{storyId}")
    public StoryResponseDto.Story story(@PathVariable int storyId) {
        return storyService.story(storyId);
    }

    // 내 스토리 조회
    @GetMapping("/mystory")
    public List<StoryResponseDto.Story> mystories() {
        return storyService.mystories(userId2);
    }
}
