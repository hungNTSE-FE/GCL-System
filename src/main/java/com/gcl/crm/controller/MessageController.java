package com.gcl.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/hello")
    public void sendToAdmin(SimpMessageHeaderAccessor sha, @Payload String username) {

        String message = "Thông báo bởi nhân viên " + sha.getUser().getName() ;


        if(username.equals("admin")){
             message = "Thông báo bởi nhân viên " + sha.getUser().getName() + " : Khách hàng cần mở tài khoản " ;

        }


        simpMessagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
    }

}
