package com.gcl.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHomePage(Model model) {
        return "/employee/home-employee-page-V2";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String getInsertPage(Model model) { return "/employee/insert-employee-page-V2"; }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditPage(Model model) { return "/employee/edit-employee-page-V2"; }
}
