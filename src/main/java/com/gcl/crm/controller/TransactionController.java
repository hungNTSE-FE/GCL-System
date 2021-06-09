package com.gcl.crm.controller;

import com.gcl.crm.entity.Task;
import com.gcl.crm.entity.Transaction;
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

    @GetMapping({"/showCreateForm"})
    public String showTransactionCreatePage(Model model){
        Transaction transaction = new Transaction();
        model.addAttribute("transaction",transaction);
        return "transaction/create-transaction-page";
    }
}
