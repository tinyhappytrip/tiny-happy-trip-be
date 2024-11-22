package com.tinyhappytrip.chat.mapper;

import com.tinyhappytrip.chat.domain.Chat;
import com.tinyhappytrip.chat.domain.ChatRoom;
import com.tinyhappytrip.chat.dto.ChatRequest;
import com.tinyhappytrip.chat.dto.ChatResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMapper {
    void insertChatMessage(Chat chat);

    List<Chat> selectAllChatBytChatRoomId(Long chatRoomId);

    Chat selectLastMessageByChatRoomId(Long chatRoomId);

    Long selectCountNoneReadCountByChatRoomId(Long userId, Long chatRoomId);
}
