package com.tinyhappytrip.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Chat {
    private Long chatId;
    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;
    private String message;
    private String sentAt;
    private boolean isRead;
}