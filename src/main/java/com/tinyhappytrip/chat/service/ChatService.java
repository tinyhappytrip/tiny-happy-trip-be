package com.tinyhappytrip.chat.service;

import com.tinyhappytrip.chat.dto.ChatRequest;
import com.tinyhappytrip.chat.dto.ChatResponse;

import java.util.List;

public interface ChatService {

    List<ChatResponse.ChatRoomDto> getAllChatRoom();

    List<ChatResponse.ChatDto> getAllChat(Long chatRoomId);

    void saveChatMessage(ChatRequest.ChatDto chatDto);

    void sendChatMessage(ChatRequest.ChatDto chatDto);

    void sendNotification(Long receiverId, String content);
}
