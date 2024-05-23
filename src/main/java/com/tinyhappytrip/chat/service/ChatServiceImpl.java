package com.tinyhappytrip.chat.service;

import com.tinyhappytrip.chat.domain.Chat;
import com.tinyhappytrip.chat.dto.ChatRequest;
import com.tinyhappytrip.chat.dto.ChatResponse;
import com.tinyhappytrip.chat.mapper.ChatMapper;
import com.tinyhappytrip.chat.mapper.ChatRoomMapper;
import com.tinyhappytrip.security.util.SecurityUtil;
import com.tinyhappytrip.user.domain.User;
import com.tinyhappytrip.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRoomMapper chatRoomMapper;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void saveChatMessage(ChatRequest.ChatDto chatDto) {
        Chat chat = chatDto.toEntity();
        if (!chatRoomMapper.isExistingChatRoom(chat)) {
            chatRoomMapper.insertChatRoom(chat);
        }
        Long chatRoomId = chatRoomMapper.findChatRoomByParticipants(chat);
        chat.setChatRoomId(chatRoomId);
        chatMapper.insertChatMessage(chat);
        chatDto.setChatRoomId(chatRoomId);
    }

    @Override
    public List<ChatResponse.ChatRoomDto> getAllChatRoom() {
        Long userId = SecurityUtil.getCurrentUserId();
        return chatRoomMapper.selectAllChatRoomsByUserId(userId).stream()
                .map(chatRoom -> {
                    Long otherParticipantId = (chatRoom.getParticipantId1() == userId) ? chatRoom.getParticipantId2() : chatRoom.getParticipantId1();
                    User otherParticipant = userMapper.selectByUserId(otherParticipantId).get();
                    Chat chat = chatMapper.selectLastMessageByChatRoomId(chatRoom.getChatRoomId());
                    Long noneReadCount = chatMapper.selectCountNoneReadCountByChatRoomId(userId, chatRoom.getChatRoomId());
                    return ChatResponse.ChatRoomDto.toChatRoomDto(chatRoom, otherParticipant, chat.getMessage(), noneReadCount, chat.getSentAt());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatResponse.ChatDto> getAllChat(Long chatRoomId) {
        return chatMapper.selectAllChatBytChatRoomId(chatRoomId).stream()
                .map(chat -> ChatResponse.ChatDto.toChatDto(chat, userMapper.selectByUserId(chat.getSenderId()).get(), userMapper.selectByUserId(chat.getReceiverId()).get()))
                .collect(Collectors.toList());
    }

    @Override
    public void sendChatMessage(ChatRequest.ChatDto chatDto) {
        messagingTemplate.convertAndSend("/sub/chats/" + chatDto.getChatRoomId(), chatDto);
    }

    @Override
    public void sendNotification(Long receiverId, String content) {
        messagingTemplate.convertAndSend("/sub/notifications/" + receiverId, content);
    }
}
