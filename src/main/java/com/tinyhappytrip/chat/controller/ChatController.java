package com.tinyhappytrip.chat.controller;

import com.tinyhappytrip.chat.dto.ChatRequest;
import com.tinyhappytrip.chat.dto.ChatResponse;
import com.tinyhappytrip.chat.service.ChatService;
import com.tinyhappytrip.chat.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;
    private final NotificationService notificationService;

    // 채팅 보내기
    @MessageMapping("/chats/send")
    public void sendMessage(ChatRequest.ChatDto chatDto) {
        System.out.println("chatDto = " + chatDto);
        chatService.saveChatMessage(chatDto);
        chatService.sendChatMessage(chatDto);
        notificationService.createNotification(chatDto.getReceiverId(), chatDto.getContent(), chatDto.getChatRoomId());
        chatService.sendNotification(chatDto.getReceiverId(), chatDto.getContent());
    }

    // 채팅 내용 조회
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<ChatResponse.ChatDto>> getAllChat(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatService.getAllChat(chatRoomId));
    }

    @GetMapping("/rooms/{receiverId}")
    public Long createAndReturnChatRoomId(@PathVariable Long receiverId) {
        System.out.println("receiverId = " + receiverId);
        System.out.println("chatService.createAndReturnChatRoomId(receiverId) = " + chatService.createAndReturnChatRoomId(receiverId));
        return chatService.createAndReturnChatRoomId(receiverId);
    }

    // 채팅 목록 가져오기
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatResponse.ChatRoomDto>> getAllChatRoom() {
        return ResponseEntity.ok(chatService.getAllChatRoom());
    }

    @GetMapping("/notifications")
    public List<ChatResponse.NotificationDto> getNotification() {
        return notificationService.getNotifications();
    }

    @PostMapping("/notifications/{notificationId}")
    public void setNotification(@PathVariable Long notificationId) {
        notificationService.setNotification(notificationId);
    }
}
