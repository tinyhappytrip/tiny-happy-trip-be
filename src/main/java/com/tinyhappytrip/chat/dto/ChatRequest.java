package com.tinyhappytrip.chat.dto;

import com.tinyhappytrip.chat.domain.Chat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ChatRequest {

    @Getter
    @Setter
    @ToString
    public static class ChatDto {
        private Long chatRoomId;
        private Long senderId;
        private Long receiverId;
        private String message;
        private String content;

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
