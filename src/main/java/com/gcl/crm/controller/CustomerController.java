package com.gcl.crm.controller;

import com.gcl.crm.form.ComboboxForm;
import com.gcl.crm.form.CustomerForm;
import com.gcl.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import javax.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private static final String ADD_CUSTOMER_PAGE = "/customer/create-customer-page-V2";
    private static final String CUSTOMER_FORM = "CustomerForm";

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String goHomePage(Model model) {
        return "/customer/home-customer-page-V2";
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
    public String addCustomerPage(Model model, @ModelAttribute("CustomerForm") CustomerForm customerForm) {
        ComboboxForm comboboxForm = customerService.initComboboxData();
        customerForm.setComboboxForm(comboboxForm);
        model.addAttribute(CUSTOMER_FORM, customerForm);
        return ADD_CUSTOMER_PAGE;
    }

    @PostMapping(value = "/registerCustomer")
    public String saveCustomer(Model model, @ModelAttribute(CUSTOMER_FORM) CustomerForm customerForm
            , BindingResult result, Errors errors) {
        customerService.registerCustomer(customerForm);
        ComboboxForm comboboxForm = customerService.initComboboxData();
        customerForm.setComboboxForm(comboboxForm);
        model.addAttribute(CUSTOMER_FORM, customerForm);
        return ADD_CUSTOMER_PAGE;
    }
}
