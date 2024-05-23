package com.tinyhappytrip.chat.dto;

import com.tinyhappytrip.chat.domain.Chat;
import com.tinyhappytrip.chat.domain.ChatRoom;
import com.tinyhappytrip.chat.domain.Notification;
import com.tinyhappytrip.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ChatResponse {

    @Getter
    @Setter
    @Builder
    public static class ChatRoomDto {
        private Long chatRoomId;
        private Long userId;
        private String nickname;
        private String userImage;
        private String lastMessage;
        private Long noneReadCount;
        private String lastSentAt;

        public static ChatResponse.ChatRoomDto toChatRoomDto(ChatRoom chatRoom, User user, String latestMessage, Long noneReadCount, String lastSentAt) {
            return ChatResponse.ChatRoomDto.builder()
                    .chatRoomId(chatRoom.getChatRoomId())
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .userImage(user.getUserImage())
                    .lastMessage(latestMessage)
                    .noneReadCount(noneReadCount)
                    .lastSentAt(lastSentAt)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class ChatDto {
        private Long chatRoomId;
        private Long senderId;
        private String senderNickname;
        private String senderImage;
        private Long receiverId;
        private String receiverNickname;
        private String receiverImage;
        private String message;
        private String sentAt;
        private boolean isRead;

        public static ChatResponse.ChatDto toChatDto(Chat chat, User sender, User receiver) {
            return ChatResponse.ChatDto.builder()
                    .chatRoomId(chat.getChatRoomId())
                    .senderId(sender.getUserId())
                    .senderNickname(sender.getNickname())
                    .senderImage(sender.getUserImage())
                    .receiverId(receiver.getUserId())
                    .receiverNickname(receiver.getNickname())
                    .receiverImage(receiver.getUserImage())
                    .message(chat.getMessage())
                    .sentAt(chat.getSentAt())
                    .isRead(chat.isRead())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class NotificationDto {
        private Long notificationId;
        private Long userId;
        private Long chatRoomId;
        private String content;
        private boolean isRead;
        private String createdAt;

        public static ChatResponse.NotificationDto toNotificationDto(Notification notification) {
            return NotificationDto.builder()
                    .notificationId(notification.getNotificationId())
                    .userId(notification.getUserId())
                    .chatRoomId(notification.getChatRoomId())
                    .content(notification.getContent())
                    .isRead(notification.isRead())
                    .createdAt(notification.getCreatedAt())
                    .build();
        }
    }
}
