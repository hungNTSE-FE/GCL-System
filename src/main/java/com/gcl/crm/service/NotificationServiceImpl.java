package com.gcl.crm.service;

import com.gcl.crm.entity.Notification;
import com.gcl.crm.repository.NotificationRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepositoty notificationRepository;

    @Override
    public boolean createNotification(Notification notification) {
        Notification noti = notificationRepository.save(notification);
        return noti != null;
    }
}
