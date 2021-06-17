package com.gcl.crm.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "App_User", uniqueConstraints = {@UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name")})
public class AppUser {

    @Id
    @GeneratedValue
    @Column(name = "User_Id", nullable = false)
    private Long userId;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    @Column(name = "Encryted_Password", length = 255, nullable = false)
    private String encrytedPassword;

    @Column(name = "Enabled", length = 1, nullable = false)
    private boolean enabled;

    @OneToOne
    @JoinColumn(name ="employee_id")
    private Employee employee;

    @ManyToMany(mappedBy = "appUsers", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notifications;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
