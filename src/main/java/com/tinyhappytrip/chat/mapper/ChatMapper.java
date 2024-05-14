package com.tinyhappytrip.chat.mapper;

import com.tinyhappytrip.chat.domain.Chat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    int insert(Long chatRoomId, Chat chat);

    List<Chat> selectAllChatBytChatRoomId(Long chatRoomId);

    String selectLastMessageByChatRoomId(Long chatRoomId);

    Long selectCountNoneReadCountByChatRoomId(Long userId, Long chatRoomId);
}
