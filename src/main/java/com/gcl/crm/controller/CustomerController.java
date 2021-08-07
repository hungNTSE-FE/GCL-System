package com.gcl.crm.controller;

import com.gcl.crm.dto.ErrorInFo;
import com.gcl.crm.entity.*;
import com.gcl.crm.enums.Gender;
import com.gcl.crm.enums.Status;
import com.gcl.crm.form.ComboboxForm;
import com.gcl.crm.form.CustomerDistributionForm;
import com.gcl.crm.form.CustomerForm;
import com.gcl.crm.service.CustomerProcessService;
import com.gcl.crm.service.CustomerService;
import com.gcl.crm.form.PotentialSearchForm;
import com.gcl.crm.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.gcl.crm.service.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
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
    MarketingGroupService marketingGroupService;

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
        User user = userService.getUserByUsername(principal.getName());
        List<Source> sources = sourceRepository.getAll();
        List<Level> levels = levelService.getAll();
        List<Customer> potentials = customerProcessService.getAllContractCustomer();
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
        model.addAttribute("userInfo", user);

        return "customer/view-potential-customer-page";
    }
    @GetMapping({"/showUpdateForm/{id}"})
    public String showContractCreatePage(@PathVariable(name="id") int id ,Model model, Principal principal){
        Customer customer = customerProcessService.findCustomerByID(id);
        User user = userService.getUserByUsername(principal.getName());

        model.addAttribute("bankAccountList",customer.getBankAccounts());
        model.addAttribute("userInfo", user);
        customer.getIdentification().setBackImageUrl("customerIdentification" + "/" + customer.getCustomerId() +"/"+ customer.getIdentification().getBackImageUrl());
        customer.getIdentification().setFrontImageUrl("customerIdentification" + "/" + customer.getCustomerId() + "/" + customer.getIdentification().getFrontImageUrl());
        System.out.println(customer.getIdentification().getBackImageUrl());
        model.addAttribute("customer",customer);
        return "customer/update-customer-page";
    }
    @RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
    public String addCustomerPage(Model model, @Nullable @RequestParam("potentialId") Long potentialId
            , Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        CustomerForm customerForm = customerService.initForm(potentialId);
        model.addAttribute(CUSTOMER_FORM, customerForm);
        model.addAttribute("userInfo", user);
        return ADD_CUSTOMER_PAGE;
    }
    @PostMapping({"/updateCustomer"})
    public String updateCustomer(@ModelAttribute("customer") Customer customer,    @RequestParam(required=false,value="bankName") String bankName,@RequestParam(required=false,value="bankNumber") String bankNumber,
                                 @RequestParam(required = false,value="gender") String gender,
                                 @RequestParam(required = false,value="dateOfBirth") String dateOfBirth,
                                 @RequestParam(required = false,value="issueDate") String issueDate, Model model
                                , Principal principal
                                 ) {
        User user = userService.getUserByUsername(principal.getName());
        Customer updateCustomer = customerProcessService.findCustomerByID(customer.getCustomerId());


        List<BankAccount> bankAccountList = updateCustomer.getBankAccounts();

        customer.getIdentification().setBirthDate(Date.valueOf(dateOfBirth));
        customer.getIdentification().setIssueDate(Date.valueOf(issueDate));

        bankAccountList.get(0).setBankName(bankName);
        bankAccountList.get(0).setId(bankNumber);
        customer.setBankAccounts(bankAccountList);
        customerProcessService.saveCustomer(customer);
        model.addAttribute("userInfo", user);
        return "redirect:/customer/viewContractCustomer";
    }

    @PostMapping(value = "/registerCustomer")
    public String registerCustomer(Model model, @Valid @ModelAttribute(CUSTOMER_FORM) CustomerForm customerForm
            , BindingResult result, Errors errors, Principal principal,@RequestParam(value="imageAfter") MultipartFile after,@RequestParam(value="imageBefore") MultipartFile before) {
        User user = userService.getUserByUsername(principal.getName());
        customerForm.setImageAfter(after);
        customerForm.setImageBefore(before);
        List<ErrorInFo> errorInFoList = customerService.checkBussinessBeforeRegistCustomer(customerForm);
        if (!CollectionUtils.isEmpty(errorInFoList)) {
            ComboboxForm comboboxForm = customerService.initComboboxData();
            List<MarketingGroup> marketingGroupList = marketingGroupService.getAllMktByStatus();
            customerForm.setComboboxForm(comboboxForm);
            customerForm.setMarketingGroupList(marketingGroupList);
            model.addAttribute(CUSTOMER_FORM, customerForm);
            model.addAttribute("userInfo", user);
            model.addAttribute("errorInfo", errorInFoList);
            return ADD_CUSTOMER_PAGE;
        }

        customerService.registerCustomer(customerForm, user,before,after);
        ComboboxForm comboboxForm = customerService.initComboboxData();
        customerForm.setComboboxForm(comboboxForm);
        model.addAttribute(CUSTOMER_FORM, customerForm);
        model.addAttribute("userInfo", user);
        return "redirect:/customer/manageCustomer";
    }

    @RequestMapping(value = "/viewContractCustomer", method = RequestMethod.GET)
    public String goHomePage(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("userName", principal.getName());
        List<Customer> customerList = customerProcessService.getAllContractCustomer();
        model.addAttribute("listCustomers",customerList);
        model.addAttribute("userInfo", user);
        return "customer/view-customer-contract-page";
    }
}
