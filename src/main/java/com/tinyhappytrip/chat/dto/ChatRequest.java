package com.tinyhappytrip.chat.dto;

import com.tinyhappytrip.chat.domain.Chat;
import lombok.Getter;
import lombok.Setter;

public class ChatRequest {

    @Getter
    @Setter
    public static class ChatDto {
        private Long chatRoomId;
        private Long senderId;
        private Long receiverId;
        private String message;

        public Chat toEntity() {
            return Chat.builder()
                    .chatRoomId(chatRoomId)
                    .senderId(senderId)
                    .receiverId(receiverId)
                    .message(message)
                    .build();
        }
    }
}
