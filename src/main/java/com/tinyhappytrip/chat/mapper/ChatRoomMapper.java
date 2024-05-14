package com.tinyhappytrip.chat.mapper;

import com.tinyhappytrip.chat.domain.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ChatRoomMapper {
    Optional<Long> selectChatRoomIdBetweenUsers(Long participantId1, Long participantId2);

    int insert(Long participantId1, Long participantId2);

    List<ChatRoom> selectAllChatRoomsByUserId(Long userId);
}
