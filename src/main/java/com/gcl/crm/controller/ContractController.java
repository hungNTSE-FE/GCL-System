package com.gcl.crm.controller;

import com.gcl.crm.entity.*;
import com.gcl.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping({"/showCreateForm/{id}"})
    public String showContractCreatePage(@PathVariable(name="id") long id ,Model model){
        Customer customer = customerProcessService.findCustomerByID(id);
        model.addAttribute("customer",customer);
        Contract contract = new Contract();

        model.addAttribute("contract",contract);
        List<BankAccount> bankAccountList = customer.getBankAccounts();
        System.out.println(bankAccountList.size());
        model.addAttribute("owner",bankAccountList.get(0).getOwnerName());
        model.addAttribute("bankAccount",bankAccountList);
        return "contract/create-contract-page";
    }

    @PostMapping({"/createContract"})
    public String createContract(@RequestParam(name="customerCode") String id,@ModelAttribute("contract") Contract contract){
        Long customerCode = Long.parseLong(id);


        Customer customer =customerProcessService.findCustomerByID(customerCode);

        TradingAccount tradingAccount = new TradingAccount(contract.getNumber(),
                0,"inactive",
                contract.getAccount_name(),
                contract.getBrokerCode(),
                contract.getBroker_name(),
                contract.getCreateDate()
                );
        contract.setTradingAccount(tradingAccount);
        customer.setContract(contract);


        customerProcessService.saveCustomer(customer);
        return "contract/view-customer-page";
    }

}
