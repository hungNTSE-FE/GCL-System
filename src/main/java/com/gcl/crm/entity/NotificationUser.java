package com.gcl.crm.entity;

import com.gcl.crm.enums.IsRead;
import com.gcl.crm.enums.Status;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Notification_User", uniqueConstraints = {
        @UniqueConstraint(name = "NOTIFICATION_USER_UK", columnNames = {"receipt_id","notification_id"})
})
public class NotificationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id", nullable = false)
    private Notification notification;

    @Column(name = "is_read", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private IsRead isRead;
}
