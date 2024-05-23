package com.tinyhappytrip.chat.mapper;

import com.tinyhappytrip.chat.domain.Notification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper {
    void insertNotification(Long userId, String content);

    List<Notification> selectNotifications(Long userId);

    void updateNotification(Long notificationId);
}
