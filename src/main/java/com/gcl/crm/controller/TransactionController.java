package com.gcl.crm.controller;

import com.gcl.crm.entity.Task;
import com.gcl.crm.entity.Transaction;
import com.gcl.crm.service.TaskService;
import com.gcl.crm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @GetMapping({"/home"})
    public  String viewTransactionPage(Model model){
        model.addAttribute("listTransactions",transactionService.getAllTransaction());

        return "transaction/home-transaction-page";
    }

    @GetMapping({"/showCreateForm"})
    public String showTransactionCreatePage(Model model){
        Transaction transaction = new Transaction();
        model.addAttribute("transaction",transaction);
        return "transaction/test";
    }
    @PostMapping({"/saveTransaction"})
    public String saveTask(@ModelAttribute("transaction") Transaction transaction){
        transactionService.createTransaction(transaction);
        return  "redirect:/transaction/home";
    }
    @GetMapping({"/showUpdateTransactionForm/{id}"})
    public String showTransactionUpdateForm(@PathVariable(name = "id") long id, Model model){
        Transaction transaction = transactionService.findTransacionById(id);
        model.addAttribute("transaction",transaction);
        return "transaction/update-transaction-page";
    }
    @PostMapping({"/updateTransaction"})
    public String updateTransaction(@ModelAttribute("transaction") Transaction transaction ){
        transactionService.createTransaction(transaction);
        return "redirect:/transaction/home";
    }
    @GetMapping({"/deleteTransaction/{id}"})
    public String deleteTask(@PathVariable(value ="id") long id){
        transactionService.deleteTransactionByID(id);
        return "redirect:/transaction/home";

    }
}
