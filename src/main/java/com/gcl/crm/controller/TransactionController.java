package com.gcl.crm.controller;

import com.gcl.crm.entity.*;
import com.gcl.crm.form.TradingForm;
import com.gcl.crm.service.CustomerProcessService;
import com.gcl.crm.service.TransactionHistoryService;
import com.gcl.crm.service.TransactionService;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private CustomerProcessService customerProcessService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionHistoryService transactionHistoryService;
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

//    @GetMapping({"/perform/{id}"})
//    public String showTransactionPerform(@PathVariable(name = "id") String id, Model model){
//        Customer customer = customerProcessService.findCustomerByID(id);
//        if(customer.
//                getContract().
//                getTradingAccount().
//                getStatus().equals("inactive")){
//
//            TradingAccount tradingAccount = customer.getContract().getTradingAccount();
//
//            model.addAttribute("tradingAccount",tradingAccount);
//            model.addAttribute("customer",customer);
//            return "transaction/active-account-page";
//        }
//        else{
//            TradingForm tradingForm = new TradingForm();
//            tradingForm.setBalance(customer.getContract().getTradingAccount().getBalance());
//            tradingForm.setTradingNumber(customer.getContract().getTradingAccount().getAccountNumber());
//            tradingForm.setMoney(0);
//            tradingForm.setBalance(customer.getContract().getTradingAccount().getBalance());
//            tradingForm.setCustomerCode(customer.getCustomerCode());
//            tradingForm.setCustomerName(customer.getCustomerName());
//            model.addAttribute("tradingForm",tradingForm);
//            model.addAttribute("customer",customer);
//
//
//
//            model.addAttribute("balance",customer.getContract().getTradingAccount().getBalance() + " " + "VND");
//            return "transaction/perform-transaction-page";
//        }
//
//    }
//    @PostMapping({"/active"})
//    public String activeAccount(@ModelAttribute("customer") Customer customer){
//        customer = customerProcessService.findCustomerByID(customer.getCustomerCode());
//        TradingAccount tradingAccount = customer.getContract().getTradingAccount();
//        tradingAccount.setStatus("active");
//        Contract contract = customer.getContract();
//        contract.setTradingAccount(tradingAccount);
//        customer.setContract(contract);
//
//        customerProcessService.saveCustomer(customer);
//    return "redirect:/transaction/perform/"+customer.getCustomerCode();
//    }
    @GetMapping({"/history"})
    public  String viewTransactionHistory(Model model){
        model.addAttribute("listTransactionHistories",transactionHistoryService.getAllTransactionHistory());

        return "transaction/history-transaction-page";
    }
//    @PostMapping({"/deposit"})
//    public String transactionDeposit( RedirectAttributes ra, @ModelAttribute("tradingForm") TradingForm tradingForm, Model model ) throws ParseException {
//        Customer customer = customerProcessService.findCustomerByID(tradingForm.getCustomerCode());
//        List<BankAccount> bankAccountList = customer.getBankAccounts();
//        TradingAccount tradingAccount = customer.getContract().getTradingAccount();
//        System.out.println(tradingForm.getBankName());
//        TransactionHistory transactionHistory = new TransactionHistory();
//        double balance = 0 ;
//        String bankSrc = "";
//
//        for(int i = 0 ;i<bankAccountList.size();i++){
//            if(bankAccountList.get(i).getId().equals(tradingForm.getBankName())){
//                if(bankAccountList.get(i).getBalance() > tradingForm.getMoney()){
//                    tradingAccount.setBalance(tradingAccount.getBalance()+tradingForm.getMoney());
//                    bankSrc = bankAccountList.get(i).getBankName();
//
//                    balance = bankAccountList.get(i).getBalance()-tradingForm.getMoney();
//                    bankAccountList.get(i).setBalance(balance);
//                    customer.getContract().setTradingAccount(tradingAccount);
//                    transactionHistory.setTransactionType("Nạp  tiền");
//                    transactionHistory.setAccountNumber(tradingAccount.getAccountNumber());
//                    transactionHistory.setMoney(tradingForm.getMoney());
//                    transactionHistory.setFee(2000);
//                    transactionHistory.setCreateDate( WebUtils.getSystemDate());
//                    transactionHistory.setCustomer(customer);
//                    transactionHistory.setCustomerName(customer.getCustomerName());
//                    transactionHistory.setBankName(bankSrc);
//
//                }else{
//                    ra.addAttribute("message","Tài khoản ngân hàng không đủ số dư"+tradingForm.getMoney());
//                }
//
//
//            }
//
//
//        }
//
//        customer.setBankAccounts(bankAccountList);
//        customerProcessService.saveCustomer(customer);
//        return "redirect:/transaction/perform/"+customer.getCustomerCode();
//
//    }
//    @PostMapping({"/withdraw"})
//    public String transactionWithdraw(RedirectAttributes ra,@ModelAttribute("tradingForm") TradingForm tradingForm ,Model model) throws ParseException {
//        Customer customer = customerProcessService.findCustomerByID(tradingForm.getCustomerCode());
//        List<BankAccount> bankAccountList = customer.getBankAccounts();
//        TradingAccount tradingAccount = customer.getContract().getTradingAccount();
//        System.out.println(tradingForm.getBankName());
//        TransactionHistory transactionHistory = new TransactionHistory();
//        String bankSrc = "";
//        double balance = 0 ;
//        for(int i = 0 ;i<bankAccountList.size();i++){
//            if(bankAccountList.get(i).getId().equals(tradingForm.getBankName())){
//                if(tradingAccount.getBalance() > tradingForm.getMoney()){
//                    tradingAccount.setBalance(tradingAccount.getBalance()-tradingForm.getMoney());
//                    bankSrc = bankAccountList.get(i).getBankName();
//                    balance = bankAccountList.get(i).getBalance()+tradingForm.getMoney();
//                    bankAccountList.get(i).setBalance(balance);
//                    customer.getContract().setTradingAccount(tradingAccount);
//                    transactionHistory.setTransactionType("Rút tiền");
//                    transactionHistory.setAccountNumber(tradingAccount.getAccountNumber());
//                    transactionHistory.setMoney(tradingForm.getMoney());
//                    transactionHistory.setFee(2000);
//                    transactionHistory.setCreateDate( WebUtils.getSystemDate());
//                    transactionHistory.setCustomer(customer);
//                    transactionHistory.setCustomerName(customer.getCustomerName());
//                    transactionHistory.setBankName(bankSrc);
//
//                }else {
//                    ra.addAttribute("message","Sô dư không đủ để rút"+tradingForm.getMoney());
//
//                }
//
//
//            }
//
//
//        }
//
//        customer.setBankAccounts(bankAccountList);
//        customerProcessService.saveCustomer(customer);
//        return "redirect:/transaction/perform/"+customer.getCustomerCode();
//
//    }
}
