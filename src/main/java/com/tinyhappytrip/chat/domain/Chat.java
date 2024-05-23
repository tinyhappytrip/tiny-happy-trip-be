package com.tinyhappytrip.chat.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Chat {
    private Long chatId;
    private Long chatRoomId;
    private Long senderId;
    private Long receiverId;
    private String message;
    private String sentAt;
    private boolean isRead;
}