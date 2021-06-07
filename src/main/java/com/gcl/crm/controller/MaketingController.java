package com.gcl.crm.controller;

import com.gcl.crm.service.MaketingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MaketingController {

    public static final String MAIN_PAGE = "makerting.html";
    @Autowired private static MaketingServices maketingServices;

    @RequestMapping(value = "/maketing")
    public String initScreen(Model model) {
        return MAIN_PAGE;

    }


}
