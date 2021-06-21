package com.gcl.crm.controller;

import com.gcl.crm.entity.*;
import com.gcl.crm.service.CustomerProcessService;
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
    private CustomerProcessService customerProcessService;
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
    @GetMapping({"/perform/{id}"})
    public String showTransactionPerform(@PathVariable(name = "id") long id, Model model){
        Customer customer = customerProcessService.findCustomerByID(id);
        if(customer.
                getContract().
                getTradingAccount().
                getStatus().equals("inactive")){

            TradingAccount tradingAccount = customer.getContract().getTradingAccount();
            model.addAttribute("tradingAccount",tradingAccount);
            model.addAttribute("customer",customer);
            return "transaction/active-account-page";
        }
        else{
            model.addAttribute("customer",customer);
            model.addAttribute("balance",customer.getContract().getTradingAccount().getBalance() + " " + "VND");
            return "transaction/perform-transaction-page";
        }

    }
    @PostMapping({"/active"})
    public String activeAccount(@ModelAttribute("customer") Customer customer){
        customer = customerProcessService.findCustomerByID(customer.getCustomerCode());
        TradingAccount tradingAccount = customer.getContract().getTradingAccount();
        tradingAccount.setStatus("active");
        Contract contract = customer.getContract();
        contract.setTradingAccount(tradingAccount);
        customer.setContract(contract);

        customerProcessService.saveCustomer(customer);
    return "redirect:/transaction/perform/"+customer.getCustomerCode();
    }
}
