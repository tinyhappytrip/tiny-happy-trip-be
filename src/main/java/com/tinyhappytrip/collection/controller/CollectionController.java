package com.tinyhappytrip.collection.controller;//package com.tinyhappytrip.collection.controller;

import com.tinyhappytrip.collection.dto.CollectionRequest;
import com.tinyhappytrip.collection.dto.CollectionResponse;
import com.tinyhappytrip.collection.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
public class CollectionController {
    private final CollectionService collectionService;

    // 플레이리스트 생성하기
    @PostMapping
    public ResponseEntity<Void> createCollection(@RequestBody CollectionRequest.CreateDto createDto) {
        System.out.println(createDto);
        return ResponseEntity.status(collectionService.createCollection(createDto) == 1 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 플레이리스트 삭제하기
    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Integer> deleteCollection(@PathVariable Long collectionId) {
        return ResponseEntity.status(collectionService.deleteCollection(collectionId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 플레이리스트 수정하기
    @PutMapping("/{collectionId}")
    public ResponseEntity<Integer> updatecollection(@Value("${image.collection}") String basePath,
                                                  @PathVariable Long collectionId,
                                                  @ModelAttribute CollectionRequest.Update update) {
        return ResponseEntity.status(collectionService.updateCollection(basePath, collectionId, update) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 플레이리스트 좋아요
    @PostMapping("/like/{collectionId}")
    public ResponseEntity<Integer> likecollection(@PathVariable Long collectionId) {
        return ResponseEntity.status(collectionService.setCollectionLike(collectionId) == 1 ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<CollectionResponse.CollectionInfo> getcollection(@PathVariable Long collectionId) {
        System.out.println("get");
        return ResponseEntity.ok(collectionService.getCollectionByCollectionId(collectionId));
    }

    // 내 플레이리스트
    @GetMapping("/user")
    public ResponseEntity<List<CollectionResponse.CollectionInfo>> getUsercollection() {
        System.out.println("내 플레이리스트");
        return ResponseEntity.ok(collectionService.getUserCollection());
    }

    // 추천 플레이리스트 (좋아요 top3)
    @GetMapping("/recommend")
    public ResponseEntity<List<CollectionResponse.CollectionInfo>> getTopThreecollection() {
        return ResponseEntity.ok(collectionService.getTopThreeCollection());
    }

    // 내가 좋아요 누른 플레이리스트
    @GetMapping("/user-like")
    public ResponseEntity<List<CollectionResponse.CollectionInfo>> getUserLikecollection() {
        return ResponseEntity.ok(collectionService.getUserLikeCollection());
    }

    // 댓글 달기
    @PostMapping("/comment/{collectionId}")
    public ResponseEntity<Void> addComment(@PathVariable Long collectionId, @RequestBody CollectionRequest.Comment comment) {
        return ResponseEntity.status(collectionService.addComment(collectionId, comment) == 1 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{collectionCommentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long collectionCommentId) {
        return ResponseEntity.status(collectionService.deleteComment(collectionCommentId) == 1 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}