package com.tinyhappytrip.chat.service;

import com.tinyhappytrip.chat.domain.Notification;
import com.tinyhappytrip.chat.dto.ChatResponse;
import com.tinyhappytrip.chat.mapper.NotificationMapper;
import com.tinyhappytrip.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationMapper notificationMapper;

    @Override
    public void createNotification(Long userId, String content, Long chatRoomId) {
        notificationMapper.insertNotification(userId, content, chatRoomId);
    }

    @Override
    public List<ChatResponse.NotificationDto> getNotifications() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<Notification> notifications = notificationMapper.selectNotifications(userId);
        List<ChatResponse.NotificationDto> result = new ArrayList<>();
        for (Notification notification : notifications) {
            result.add(ChatResponse.NotificationDto.toNotificationDto(notification));
        }
        return result;
    }

    @Override
    public void setNotification(Long notificationId) {
        notificationMapper.updateNotification(notificationId);
    }


}
