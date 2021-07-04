package com.gcl.crm.controller;

import com.gcl.crm.entity.*;
import com.gcl.crm.form.ComboboxForm;
import com.gcl.crm.form.CustomerForm;
import com.gcl.crm.service.CustomerProcessService;
import com.gcl.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private static final String ADD_CUSTOMER_PAGE = "/customer/create-customer-page-V2";
    private static final String CUSTOMER_FORM = "CustomerForm";

    @Autowired
    CustomerProcessService customerProcessService;

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String goHomePage(Model model) {
        return "/customer/home-customer-page-V2";
    }

    @GetMapping({"/manageCustomer"})
    public  String viewCustomer(Model model){
        model.addAttribute("listCustomers",customerProcessService.getAllCustomer());

        return "customer/view-customer-page";
    }
    @GetMapping({"/showUpdateForm/{id}"})
    public String showContractCreatePage(@PathVariable(name="id") long id ,Model model){
        Customer customer = customerProcessService.findCustomerByID(id);
        List<BankAccount> bankAccountList = customer.getBankAccounts();
        System.out.println(bankAccountList.size());

        model.addAttribute("customer",customer);
        Contract contract = new Contract();
        model.addAttribute("contract",contract);

        model.addAttribute("bankAccountList",bankAccountList);
        System.out.println(customer.getBankAccounts().get(0).getBankName());

        return "customer/update-customer-page";
    }
    @RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
    public String addCustomerPage(Model model, @ModelAttribute("CustomerForm") CustomerForm customerForm) {
        ComboboxForm comboboxForm = customerService.initComboboxData();
        customerForm.setComboboxForm(comboboxForm);
        model.addAttribute(CUSTOMER_FORM, customerForm);
        return ADD_CUSTOMER_PAGE;
    }
//    @PostMapping(value = "/updateCustomer")
//    public String updateCustomer(@RequestParam(name="customerID") long id,Model model, @ModelAttribute(CUSTOMER_FORM) CustomerForm customerForm
//            , BindingResult result, Errors errors) throws ParseException {
//        List<BankAccount> bankAccountList = new ArrayList<>();
//        Customer customer = new Customer();
//        customer.setCustomerCode(id);
//        customer.setCreateDate(WebUtils.getSystemDate());
//        customer.setUpdDate(WebUtils.getSystemDate());
//        customer.setCustomerName(customerForm.getCustomerName());
//        customer.setDescription(customerForm.getDescription());
//        customer.setEmail(customerForm.getEmail());
//        customer.setGender(Gender.findByOption(customerForm.getGender()));
//        customer.setPhoneNumber(customerForm.getPhoneNumber());
//        customer.setStatus(customerForm.getStatus());
////        BankAccount bankAccount = customerService.registerBanking(customerForm);
////        customer.setBankAccounts(bankAccountList);
//        Identification identification = customerService.registerIdentification(customerForm);
//        customer.setIdentification(identification);
//    customerProcessService.saveCustomer(customer);
//
//
//        return "redirect:/customer/manageCustomer";
//    }

    @PostMapping(value = "/registerCustomer")
    public String registerCustomer(Model model, @Valid @ModelAttribute(CUSTOMER_FORM) CustomerForm customerForm
            , BindingResult result, Errors errors) {
        customerService.registerCustomer(customerForm);
        ComboboxForm comboboxForm = customerService.initComboboxData();
        customerForm.setComboboxForm(comboboxForm);
        model.addAttribute(CUSTOMER_FORM, customerForm);
        return "redirect:/customer/manageCustomer";
    }

    @GetMapping({"/deleteCustomer/{id}"})
    public String deleteCustomer(@PathVariable(value ="id") long id){
        customerProcessService.deleteCustomer(id);
        return "redirect:/customer/manageCustomer";
    }

    @PostMapping(value = "/saveCustomer")
    public String saveCustomer(Model model, @ModelAttribute(CUSTOMER_FORM) CustomerForm customerForm
            , BindingResult result, Errors errors) {
        WKCustomer wkCustomer = customerService.saveCustomer(customerForm);
        ComboboxForm comboboxForm = customerService.initComboboxData();
        customerForm.setComboboxForm(comboboxForm);
        customerForm.setHdnCustomerCode(Optional.ofNullable(wkCustomer)
                                                    .map(WKCustomer::getCustomerCode)
                                                    .orElse(null));
        model.addAttribute(CUSTOMER_FORM, customerForm);
        return ADD_CUSTOMER_PAGE;
    }

}
