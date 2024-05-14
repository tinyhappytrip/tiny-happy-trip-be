package com.tinyhappytrip.chat.service;

import com.tinyhappytrip.chat.dto.ChatRequest;
import com.tinyhappytrip.chat.dto.ChatResponse;

import java.util.List;

public interface ChatService {
    Long findOrCreateChatRoom(Long participantId2);

    List<ChatResponse.ChatRoomDto> getAllChatRoom();

    List<ChatResponse.ChatDto> getAllChat(Long chatRoomId);

    int saveChat(Long chatRoomId, ChatRequest.ChatDto chat);
}
