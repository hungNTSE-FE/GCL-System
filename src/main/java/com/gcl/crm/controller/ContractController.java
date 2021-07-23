package com.gcl.crm.controller;

import com.gcl.crm.entity.*;
import com.gcl.crm.service.*;
import com.gcl.crm.utils.WebUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    ContractService contractService;

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerProcessService customerProcessService;


    @Autowired
    private TransactionService transactionService;
    @GetMapping({"/manageCustomer"})
    public  String viewCustomer(Model model){
        model.addAttribute("listCustomers",customerProcessService.getAllCustomer());
        System.out.println(contractService.getContractID());
        return "contract/view-customer-page";
    }
    @GetMapping({"/manageTradingAccount"})
    public  String viewTradingAccount(Model model){
        model.addAttribute("listCustomers",customerProcessService.getCustomerHaveTradingAccount());

        return "contract/view-tradingAccount-page";
    }


    @GetMapping({"/openAccount/{id}"})
    public String showOpenAccountPage(@PathVariable(name="id") int id ,Model model) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Customer customer = customerProcessService.findCustomerByID(id);
        model.addAttribute("customer",customer);
        customer.setBirthDate(format.format(customer.getIdentification().getBirthDate()));
        customer.setIssueDate(format.format(customer.getIdentification().getIssueDate()));

        TradingAccount tradingAccount = new TradingAccount();
        tradingAccount.setCustomerID(id);
        model.addAttribute("tradingAccount",tradingAccount);
        List<BankAccount> bankAccountList = customer.getBankAccounts();

        System.out.println(bankAccountList.size());
        model.addAttribute("owner",bankAccountList.get(0).getOwnerName());
        model.addAttribute("bankAccount",bankAccountList);
        return "contract/open-account-page";
    }
    @GetMapping({"/showCreateContract/{id}"})
    public String showContractCreatePage(@PathVariable(name="id") int id ,Model model){
        Customer customer = customerProcessService.findCustomerByID(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        customer.setBirthDate(format.format(customer.getIdentification().getBirthDate()));
        customer.setIssueDate(format.format(customer.getIdentification().getIssueDate()));
        String strDate = customer.getCreateDate()+"";
        String[] createDate = strDate.split(" ");
        model.addAttribute("createDate",createDate[0]);
        Contract contract = new Contract();
        contract.setCustomer(customer);
        contract.setId(contractService.getContractID());
        model.addAttribute("contract",contract);
        model.addAttribute("customer",customer);
        model.addAttribute("customerID",customer.getCustomerId());

        return "contract/create-contract-page";
    }

    @PostMapping({"/createAccount"})
    public String createTradingAccount(@ModelAttribute("tradingAccount") TradingAccount tradingAccount)  {
        Customer customer =customerProcessService.findCustomerByID(tradingAccount.getCustomerID());
        customerProcessService.createTradingAccount(tradingAccount,customer);
        return "redirect:/contract/manageCustomer";
    }
    @GetMapping({"/active/{id}"})
    public String activateAccount(@PathVariable(name="id") int id) throws ParseException {
        Customer customer = customerProcessService.findCustomerByID(id);
        customerProcessService.activateTradingAccount(customer);
        return "redirect:/contract/manageTradingAccount";
    }
    @PostMapping({"/createContract"})
    public String createContract(@ModelAttribute("contract") Contract contract) throws ParseException {
        Customer customer = customerProcessService.findCustomerByID(contract.getCustomer().getCustomerId());
        customerProcessService.createContract(contract,customer);
        return "redirect:/contract/manageCustomer";
    }

    @GetMapping({"/showUpdateAccountForm/{id}"})
    public String showUpdateAccountForm(@PathVariable(name="id") int id ,Model model){
        Customer customer = customerProcessService.findCustomerByID(id);

        model.addAttribute("customer",customer);
        model.addAttribute("tradingAccount",customer.getTradingAccount());
        return "contract/update-account-page";
    }

    @PostMapping({"/updateAccount"})
    public String updateTradingAccount(@ModelAttribute("tradingAccount") TradingAccount tradingAccount) throws ParseException {
        Customer customer =customerProcessService.findCustomerByID(tradingAccount.getCustomerID());
        customer.setNumber(customer.getCustomerCode());
        tradingAccount.setAccountName(customer.getCustomerName());
        tradingAccount.setCreateDate(customer.getTradingAccount().getCreateDate());
        tradingAccount.setUpdateDate(Date.valueOf(LocalDate.now()));
        tradingAccount.setUpdateType(tradingAccount.getStatus());

        tradingAccount.setCustomer(customer);
        customer.setTradingAccount(tradingAccount);


        customerProcessService.saveCustomer(customer);
        return "redirect:/contract/manageTradingAccount";
    }

}
