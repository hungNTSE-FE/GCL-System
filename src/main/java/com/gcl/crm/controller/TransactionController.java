package com.gcl.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @GetMapping({"/home"})
    public  String viewTransactionPage(){
        return "transaction/home-transaction-page";
    }
}
