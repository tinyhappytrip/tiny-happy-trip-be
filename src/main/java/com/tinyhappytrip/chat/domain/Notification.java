package com.tinyhappytrip.chat.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Notification {
    private Long notificationId;
    private Long userId;
    private String content;
    private boolean isRead;
    private String createdAt;
}
