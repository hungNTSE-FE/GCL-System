package com.gcl.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
@RequestMapping("/deal")
public class DealController {
    private static final String HOME_PAGE = "/deal/home-page";

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String goHomePage(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return HOME_PAGE;
    }
}
