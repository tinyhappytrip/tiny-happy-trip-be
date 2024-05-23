package com.tinyhappytrip.chat.service;

import com.tinyhappytrip.chat.dto.ChatResponse;

import java.util.List;

public interface NotificationService {
    void createNotification(Long receiverId, String str);

    List<ChatResponse.NotificationDto> getNotifications();

    void setNotification(Long notificationId);
}
