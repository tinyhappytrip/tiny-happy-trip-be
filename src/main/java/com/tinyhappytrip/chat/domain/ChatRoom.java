package com.tinyhappytrip.chat.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
    private Long chatRoomId;
    private Long participantId1;
    private Long participantId2;

}