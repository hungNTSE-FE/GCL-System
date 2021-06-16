package com.gcl.crm.controller;

import com.gcl.crm.entity.PaymentMethod;
import com.gcl.crm.entity.Transaction;
import com.gcl.crm.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/contract")
public class ContractController {
//    @GetMapping({"/home"})
//    public  String viewTransactionPage(){
//        return "transaction/home-transaction-page";
//    }
    @Autowired
    PaymentMethodService paymentMethodService;
    @GetMapping({"/showCreateForm"})
    public String showContractCreatePage(Model model){
        List<PaymentMethod> paymentMethodList = paymentMethodService.getAllPaymentMethod();
        model.addAttribute("payments", paymentMethodList);

        return "contract/create-contract-page";
    }
}
