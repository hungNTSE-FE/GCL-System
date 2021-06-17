package com.gcl.crm.controller;

import com.gcl.crm.constants.MyConstants;
import com.gcl.crm.entity.AppUser;
import com.gcl.crm.entity.Employee;
import com.gcl.crm.entity.Notification;
import com.gcl.crm.repository.EmployeeRepository;
import com.gcl.crm.repository.UserRepository2;
import com.gcl.crm.service.EmployeeService;
import com.gcl.crm.service.NotificationService;
import com.gcl.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    UserRepository2 userRepository2;

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @Autowired
    public JavaMailSender emailSender;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String goCreatePage(Model model) {
        List<AppUser> appUsers = userRepository2.findAllByEnabled(true);
        model.addAttribute("appUsers", appUsers);
        return "/notification/insert_notification_page_V2";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "notification")
    public String create(Model model,
                         @ModelAttribute("notification") Notification notification,
                         @RequestParam("select2") List<Long> selectUser,
                         @RequestParam("topic") String topic,
                         @RequestParam("upload") String upload,
                         @RequestParam("describe") String describe,
                         Principal principal,
                         RedirectAttributes redirectAttributes
    ) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser appUser = userRepository2.findAppUserByUserName(loginedUser.getUsername());
        appUser.getUserId();
        notification.setAppUser(appUser);
        notification.setCreated_at(getCurrentDate());
        notification.setTopic(topic);
        notification.setMessage(describe);
        notificationService.createNotification(notification, selectUser);
        return "redirect:/notification/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "sendemail")
    public String sendEmail(Model model, @ModelAttribute("notification") Notification notification,
                            @RequestParam("topic") String topic,
                            @RequestParam("describe") String describe,
                            @RequestParam("select2") List<Long> selectUser,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        SimpleMailMessage message = new SimpleMailMessage();
        List<AppUser> appUsers = userService.getAppUsersByIdList(selectUser);
        for (int i = 0; i < appUsers.size(); i++) {
            message.setTo(appUsers.get(i).getEmployee().getCompanyEmail());
            message.setSubject(topic);
            message.setText(describe);
            this.emailSender.send(message);
        }
        return "redirect:/notification/create";
    }

    private Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

}
