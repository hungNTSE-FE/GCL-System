package com.gcl.crm.controller;

import com.gcl.crm.entity.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contract")
public class ContractController {
//    @GetMapping({"/home"})
//    public  String viewTransactionPage(){
//        return "transaction/home-transaction-page";
//    }

    @GetMapping({"/showCreateForm"})
    public String showContractCreatePage(Model model){

        return "contract/create-contract-page";
    }
}
