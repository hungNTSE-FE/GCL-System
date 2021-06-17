package com.gcl.crm.controller;

import com.gcl.crm.entity.AppUser;
import com.gcl.crm.entity.Notification;
import com.gcl.crm.entity.NotificationUser;
import com.gcl.crm.enums.IsRead;
import com.gcl.crm.repository.UserRepository2;
import com.gcl.crm.service.NotificationService;
import com.gcl.crm.service.NotificationUserService;
import com.gcl.crm.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    UserRepository2 userRepository2;

    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationUserService notificationUserService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String goCreatePage(Model model) {
        return "/notification/insert_notification_page_V2";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model,
                         @ModelAttribute("notificationForm") Notification notification,
                         RedirectAttributes redirectAttributes,
                         @RequestParam("select2") String selectUser,
                         @RequestParam("topic") String topic,
                         @RequestParam("upload") String upload,
                         @RequestParam("describe") String describe,
                         Principal principal
    ) {
        String message = "Thông báo đã được tạo thành công!";
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser appUser = userRepository2.findAppUserByUserName(loginedUser.getUsername());
        appUser.getUserId();
        notification.setAppUser(appUser);
        System.out.println("user id:" + appUser.getUserId());

        notification.setCreated_at(getCurrentDate());
        System.out.println("datetime:" + getCurrentDate());

        notification.setTopic(topic);
        notification.setMessage(describe);
//        notificationUser.setIsRead(IsRead.NOT_READ);
        boolean done = notificationService.createNotification(notification);
//        boolean doneNotiUser = notificationUserService.addNotificationUser(notificationUser);
        return "redirect:/notification/create";
    }



    private Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

}
