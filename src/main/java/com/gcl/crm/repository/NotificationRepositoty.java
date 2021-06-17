package com.gcl.crm.repository;

import com.gcl.crm.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepositoty extends JpaRepository<Notification, Long> {
}
