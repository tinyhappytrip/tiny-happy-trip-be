package com.tinyhappytrip.chat.mapper;

import com.tinyhappytrip.chat.domain.Chat;
import com.tinyhappytrip.chat.domain.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    boolean isExistingChatRoom(Chat chat);

    Long findChatRoomByParticipants(Chat chat);

    void insertChatRoom(Chat chat);

    List<ChatRoom> selectAllChatRoomsByUserId(Long userId);
}
