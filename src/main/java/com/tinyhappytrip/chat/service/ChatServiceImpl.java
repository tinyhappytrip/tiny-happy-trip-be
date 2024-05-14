package com.tinyhappytrip.chat.service;

import com.tinyhappytrip.chat.dto.ChatRequest;
import com.tinyhappytrip.chat.dto.ChatResponse;
import com.tinyhappytrip.chat.mapper.ChatMapper;
import com.tinyhappytrip.chat.mapper.ChatRoomMapper;
import com.tinyhappytrip.security.util.SecurityUtil;
import com.tinyhappytrip.user.domain.User;
import com.tinyhappytrip.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRoomMapper chatRoomMapper;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;

    @Override
    public Long findOrCreateChatRoom(Long participantId2) {
        Long participantId1 = SecurityUtil.getCurrentUserId();
        return chatRoomMapper.selectChatRoomIdBetweenUsers(participantId1, participantId2)
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    chatRoomMapper.insert(participantId1, participantId2);
                    return chatRoomMapper.selectChatRoomIdBetweenUsers(participantId1, participantId2).get();
                });
    }

    @Override
    public List<ChatResponse.ChatRoomDto> getAllChatRoom() {
        Long userId = SecurityUtil.getCurrentUserId();
        return chatRoomMapper.selectAllChatRoomsByUserId(userId).stream()
                .map(chatRoom -> {
                    Long otherParticipantId = (chatRoom.getParticipantId1() == userId) ? chatRoom.getParticipantId2() : chatRoom.getParticipantId1();
                    User otherParticipant = userMapper.selectByUserId(otherParticipantId).get();
                    String latestMessage = chatMapper.selectLastMessageByChatRoomId(chatRoom.getChatRoomId());
                    Long noneReadCount = chatMapper.selectCountNoneReadCountByChatRoomId(userId, chatRoom.getChatRoomId());
                    return ChatResponse.ChatRoomDto.toChatRoomDto(chatRoom, otherParticipant, latestMessage, noneReadCount);
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
    public int saveChat(Long chatRoomId, ChatRequest.ChatDto chat) {
        return chatMapper.insert(chatRoomId, chat.toEntity());
    }
}
