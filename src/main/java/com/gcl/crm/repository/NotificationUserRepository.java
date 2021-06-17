package com.gcl.crm.repository;

import com.gcl.crm.entity.Notification;
import com.gcl.crm.entity.NotificationUser;
import com.gcl.crm.enums.IsRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationUserRepository extends JpaRepository<NotificationUser, Long> {
    List<NotificationUser>  findAllByIsRead(IsRead isRead);
    NotificationUser findNotificationUserByIdAndAndIsRead(Long id, IsRead isRead);
    NotificationUser findNotificationUserByIsRead(IsRead isRead);
}
