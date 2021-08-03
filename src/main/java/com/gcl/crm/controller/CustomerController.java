package com.gcl.crm.controller;

import com.gcl.crm.dto.ErrorInFo;
import com.gcl.crm.entity.*;
import com.gcl.crm.form.ComboboxForm;
import com.gcl.crm.form.CustomerDistributionForm;
import com.gcl.crm.form.CustomerForm;
import com.gcl.crm.form.PotentialSearchForm;
import com.gcl.crm.repository.SourceRepository;
import com.gcl.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.validation.Valid;
import java.security.Principal;
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

    @Autowired
    UserService userService;
    @Autowired
    SourceRepository sourceRepository;
    @Autowired
    LevelService levelService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;
    @GetMapping({"/manageCustomer"})
    public  String viewCustomer(Model model){
        model.addAttribute("listCustomers",customerProcessService.getAllCustomer());

        return "customer/view-customer-page";
    }
    @GetMapping({"/managePotentialCustomer"})
    public  String viewPotentialCustomer(Model model, Principal principal){
        List<Source> sources = sourceRepository.getAll();
        List<Level> levels = levelService.getAll();
        List<Customer> potentials = customerProcessService.getAllPotentialCustomer();
        System.out.println("size" +potentials.size());
        List<Department> departments = departmentService.findAllDepartments();
        List<Employee> employees = employeeService.getAllWorkingEmployees();
        PotentialSearchForm searchForm = new PotentialSearchForm();
        model.addAttribute("departments", departments);
        CustomerDistributionForm customerDistributionForm = new CustomerDistributionForm();
        model.addAttribute("sources", sources);
        model.addAttribute("levels", levels);
        model.addAttribute("potentials", potentials);
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("employees", employees);
        model.addAttribute("customerDistributionForm", customerDistributionForm);
        model.addAttribute("userName", principal.getName());

        return "customer/view-potential-customer-page";
    }
    @GetMapping({"/showUpdateForm/{id}"})
    public String showContractCreatePage(@PathVariable(name="id") int id ,Model model){
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
    public String addCustomerPage(Model model, @Nullable @RequestParam("potentialId") Long potentialId) {
        CustomerForm customerForm = customerService.initForm(potentialId);
        model.addAttribute(CUSTOMER_FORM, customerForm);
        return ADD_CUSTOMER_PAGE;
    }

    @PostMapping(value = "/registerCustomer")
    public String registerCustomer(Model model, @Valid @ModelAttribute(CUSTOMER_FORM) CustomerForm customerForm
            , BindingResult result, Errors errors, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<ErrorInFo> errorInFoList = customerService.checkBussinessBeforeRegistCustomer(customerForm);
        if (!CollectionUtils.isEmpty(errorInFoList)) {
            ComboboxForm comboboxForm = customerService.initComboboxData();
            customerForm.setComboboxForm(comboboxForm);
            model.addAttribute(CUSTOMER_FORM, customerForm);
            model.addAttribute("errorInfo", errorInFoList);
            return ADD_CUSTOMER_PAGE;
        }
        customerService.registerCustomer(customerForm, user);
        ComboboxForm comboboxForm = customerService.initComboboxData();
        customerForm.setComboboxForm(comboboxForm);
        model.addAttribute(CUSTOMER_FORM, customerForm);
        return "redirect:/customer/manageCustomer";
    }

    @GetMapping({"/deleteCustomer/{id}"})
    public String deleteCustomer(@PathVariable(value ="id") long id){
        return "redirect:/customer/manageCustomer";
    }

}
