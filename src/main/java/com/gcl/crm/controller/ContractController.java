package com.gcl.crm.controller;

import com.gcl.crm.entity.*;
import com.gcl.crm.service.*;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/contract")
public class ContractController {
//    @GetMapping({"/home"})
//    public  String viewTransactionPage(){
//        return "transaction/home-transaction-page";
//    }
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

        return "contract/view-customer-page";
    }
    @GetMapping({"/manageTradingAccount"})
    public  String viewTradingAccount(Model model){
        model.addAttribute("listCustomers",customerProcessService.getCustomerHaveTradingAccount());

        return "contract/view-tradingAccount-page";
    }

    @GetMapping({"/openAccount/{id}"})
    public String showOpenAccountPage(@PathVariable(name="id") String id ,Model model){
        Customer customer = customerProcessService.findCustomerByID(id);
        model.addAttribute("customer",customer);
        TradingAccount tradingAccount = new TradingAccount();
        tradingAccount.setAccountNumber(customer.getCustomerCode());
        model.addAttribute("tradingAccount",tradingAccount);
        List<BankAccount> bankAccountList = customer.getBankAccounts();
        System.out.println(bankAccountList.size());
        model.addAttribute("owner",bankAccountList.get(0).getOwnerName());
        model.addAttribute("bankAccount",bankAccountList);
        return "contract/open-account-page";
    }
    @GetMapping({"/showCreateContract/{id}"})
    public String showContractCreatePage(@PathVariable(name="id") String id ,Model model){
        Customer customer = customerProcessService.findCustomerByID(id);
        String strDate = customer.getCreateDate()+"";
        String[] createDate = strDate.split(" ");
        model.addAttribute("createDate",createDate[0]);
        Contract contract = new Contract();
        model.addAttribute("contract",contract);
        model.addAttribute("customer",customer);

        return "contract/create-contract-page";
    }

    @PostMapping({"/createAccount"})
    public String createTradingAccount(@RequestParam(name="customerCode") String customerCode,@ModelAttribute("tradingAccount") TradingAccount tradingAccount) throws ParseException {
        System.out.println(customerCode);
        Customer customer =customerProcessService.findCustomerByID(customerCode);
        System.out.println(customer.getCustomerName());
        customer.setNumber(customer.getCustomerCode());
        tradingAccount.setCreateDate(WebUtils.getSystemDate());
        tradingAccount.setUpdateDate(WebUtils.getSystemDate());
        tradingAccount.setUpdateType("Inactive");
        tradingAccount.setAccountNumber(customer.getNumber());
        tradingAccount.setAccountName(customer.getCustomerName());
        tradingAccount.setCustomer(customer);
        customer.setTradingAccount(tradingAccount);


        customerProcessService.saveCustomer(customer);
        return "redirect:/contract/manageCustomer";
    }
        @GetMapping({"/active/{id}"})
        public String activateAccount(@PathVariable(name="id") String id) throws ParseException {
        Customer customer = customerProcessService.findCustomerByID(id);
        TradingAccount tradingAccount = customer.getTradingAccount();
        tradingAccount.setStatus("Active");
        tradingAccount.setUpdateType("Active");
        tradingAccount.setUpdateDate(WebUtils.getSystemDate());
        tradingAccount.setActiveDate(WebUtils.getSystemDate());
            System.out.println(tradingAccount.getActiveDate());
        tradingAccount.setCustomer(customer);
        customer.setTradingAccount(tradingAccount);

        customerProcessService.saveCustomer(customer);
        return "redirect:/contract/manageTradingAccount";
    }
    @PostMapping({"/createContract"})
    public String createContract(@RequestParam(name="customerCode") String customerCode,@ModelAttribute("contract") Contract contract) throws ParseException {

        Customer customer =customerProcessService.findCustomerByID(customerCode);
        contract.setCreateDate( WebUtils.getSystemDate());
        contract.setAccount_name(customer.getTradingAccount().getAccountName());
        contract.setBroker_name(customer.getTradingAccount().getBrokerName());
        contract.setBrokerCode(customer.getTradingAccount().getBrokerCode());
        contract.setNumber(customer.getTradingAccount().getAccountNumber());
        contract.setId(contract.getId());
        customer.setContractNumber(contract.getId());
        contract.setCustomer(customer);
        customer.setContract(contract);

        customerProcessService.saveCustomer(customer);
        return "redirect:/contract/manageCustomer";
    }
    @GetMapping({"/showUpdateAccountForm/{id}"})
    public String showUpdateAccountForm(@PathVariable(name="id") String id ,Model model){
        Customer customer = customerProcessService.findCustomerByID(id);

        model.addAttribute("customer",customer);
        model.addAttribute("tradingAccount",customer.getTradingAccount());
        return "contract/update-account-page";
    }
    @PostMapping({"/updateAccount"})
    public String updateTradingAccount(@RequestParam(name="customerCode") String customerCode,@ModelAttribute("tradingAccount") TradingAccount tradingAccount) throws ParseException {
        Customer customer =customerProcessService.findCustomerByID(customerCode);
        System.out.println(customer.getCustomerName());
        customer.setNumber(customer.getCustomerCode());
        System.out.println(customer.getNumber());
        tradingAccount.setAccountName(customer.getCustomerName());
        tradingAccount.setCreateDate(customer.getTradingAccount().getCreateDate());
        tradingAccount.setUpdateDate(WebUtils.getSystemDate());
        tradingAccount.setUpdateType(tradingAccount.getStatus());

        tradingAccount.setCustomer(customer);
        customer.setTradingAccount(tradingAccount);


        customerProcessService.saveCustomer(customer);
        return "redirect:/contract/manageTradingAccount";
    }

}
