package com.tinyhappytrip.chat.controller;

import com.tinyhappytrip.chat.dto.ChatRequest;
import com.tinyhappytrip.chat.dto.ChatResponse;
import com.tinyhappytrip.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    // 채팅 번호 조회 및 생성
    @GetMapping("/rooms/{participantId2}")
    public ResponseEntity<Long> findOrCreateChatRoom(@PathVariable Long participantId2) {
        Long res = chatService.findOrCreateChatRoom(participantId2);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // 채팅 목록 가져오기
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatResponse.ChatRoomDto>> getAllChatRoom() {
        return ResponseEntity.ok(chatService.getAllChatRoom());
    }

    // 채팅 내용 조회
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<ChatResponse.ChatDto>> getAllChat(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatService.getAllChat(chatRoomId));
    }

    // 보낸 메세지 저장
    @PostMapping("/{chatRoomId}")
    public ResponseEntity<Void> saveChat(@PathVariable Long chatRoomId, @RequestBody ChatRequest.ChatDto chat) {
        chatService.saveChat(chatRoomId, chat);
        return ResponseEntity.ok().build();
    }
}
