package com.gcl.crm.service;

import com.gcl.crm.entity.NotificationUser;
import com.gcl.crm.repository.NotificationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationUserServiceImpl implements NotificationUserService{
    @Autowired
    NotificationUserRepository notificationUserRepository;

    @Override
    public boolean addNotificationUser(NotificationUser notificationUser) {
        NotificationUser notiUser = notificationUserRepository.save(notificationUser);
        return false;
    }
}
