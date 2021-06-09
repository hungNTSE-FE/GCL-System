package com.gcl.crm.service;

import com.gcl.crm.entity.Notification;
import com.gcl.crm.enums.IsRead;
import com.gcl.crm.repository.NotificationRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepositoty notificationRepository;

    @Autowired
    UserService userService;


    @Override
    public boolean createNotification(Notification notification, List<Long> appUserIds) {
        notification.setIsRead(IsRead.NOT_READ);
        notification.setAppUsers(userService.getAppUsersByIdList(appUserIds));
        notification = notificationRepository.save(notification);
        return notification != null;
    }
}
