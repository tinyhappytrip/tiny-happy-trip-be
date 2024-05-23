package com.tinyhappytrip.chat.dto;

import com.tinyhappytrip.chat.domain.Chat;
import lombok.*;

public class ChatRequest {

    @NoArgsConstructor // 기본 생성자 추가
    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class ChatDto {
        private Long chatRoomId;
        private Long senderId;
        private Long receiverId;
        private String message;
        private String content;
        private String senderNickname;
        private String senderImage;
        private String sentAt;

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
