package com.gcl.crm.controller;

import com.gcl.crm.dto.ErrorInFo;
import com.gcl.crm.dto.SummaryMKTReport;
import com.gcl.crm.entity.*;
import com.gcl.crm.enums.Gender;
import com.gcl.crm.enums.LevelEnum;
import com.gcl.crm.enums.Status;
import com.gcl.crm.form.*;
import com.gcl.crm.repository.MarketingRepository;
import com.gcl.crm.service.CustomerProcessService;
import com.gcl.crm.service.CustomerService;
import com.gcl.crm.repository.SourceRepository;
import com.gcl.crm.utils.ExcelReader;
import com.gcl.crm.utils.WebUtils;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.gcl.crm.service.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private static final String ADD_CUSTOMER_PAGE = "/customer/create-customer-page-V2";
    private static final String CUSTOMER_FORM = "CustomerForm";
    private static final String VIEW_CUSTOMER_PAGE = "/customer/view-customer-page-v2";
    private static final String VIEW_TRADING_ACCOUNT_PAGE = "/customer/view-tradingAccount-page";
    private static final String OPEN_ACCOUNT_PAGE = "/customer/open-account-page";
    private static final String CREATE_CONTRACT_PAGE = "/customer/create-contract-page";
    private static final String  WAIT_CUSTOMER_PAGE="/customer/view-waiting-customer-page";
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
    TradingAccountService tradingAccountService;
    @Autowired
    ContractService contractService;
    @Autowired
    EmployeeService employeeService;

    @Autowired
    PotentialService potentialService;

    @Autowired
    MarketingRepository marketingRepository;

///
    @GetMapping({"/manageCustomer"})
    public  String viewCustomer(Model model, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        CustomerSearchForm customerSearchForm = new CustomerSearchForm();

        model.addAttribute("userInfo", currentUser);
        model.addAttribute("listCustomers",customerProcessService.getCustomerWaitingContract());
        model.addAttribute("searchForm",customerSearchForm);
        return VIEW_CUSTOMER_PAGE;
    }
    @GetMapping({"/waitingCustomer"})
    public  String getWaitingCustomer(Model model, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        CustomerSearchForm customerSearchForm = new CustomerSearchForm();
        model.addAttribute("searchForm",customerSearchForm);
        model.addAttribute("userInfo", currentUser);
        model.addAttribute("listCustomers",customerProcessService.getWaitingCustomer());
        return "/customer/view-waiting-customer-page";
    }

    @RequestMapping(value = "/searchWaitingContract", method = RequestMethod.GET)
    public String searchWaitingContractCustomer(Model model, @Nullable @ModelAttribute("searchForm") CustomerSearchForm searchForm, Principal principal){
        if (searchForm == null){
            return "redirect:/contract/manageTradingAccount";
        }
        System.out.println(searchForm.getEmail());
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("userInfo", currentUser);
        model.addAttribute("listCustomers",customerProcessService.findWaitingContractCustomer(searchForm));
        return VIEW_CUSTOMER_PAGE;


    }

    @RequestMapping(value = "/searchWaitingCustomer", method = RequestMethod.GET)
    public String searchWaitingCustomer(Model model, @Nullable @ModelAttribute("searchForm") CustomerSearchForm searchForm, Principal principal){
        if (searchForm == null){
            return "redirect:/customer/manageTradingAccount";
        }
        List<Customer> customerList = customerProcessService.findWaitingCustomer(searchForm);
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("userInfo", currentUser);
        model.addAttribute("listCustomers",customerProcessService.findWaitingCustomer(searchForm));
        return WAIT_CUSTOMER_PAGE;

    }
    @GetMapping({"/manageOpenAccountPage"})
    public  String viewOpenAccountPage(Model model, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("userInfo", currentUser);
        model.addAttribute("userName",principal.getName());
        model.addAttribute("listCustomers",customerProcessService.getAllCustomer());
        return "contract/view-open-account-page";
    }
    @GetMapping({"/manageCustomerAccount"})
    public  String viewTradingAccount(Model model,Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        CustomerSearchForm customerForm = new CustomerSearchForm();
        model.addAttribute("customerForm",customerForm);
        model.addAttribute("userInfo", currentUser);

        model.addAttribute("listCustomers",customerProcessService.getCustomerHaveTradingAccount());

        return VIEW_TRADING_ACCOUNT_PAGE;
    }
    @GetMapping({"/manageAccountBalance"})
    public  String viewTradingAccountBalance(Model model, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(30);
        numberFormat.setGroupingUsed(true);
        TradingAccountSearchForm searchForm = new TradingAccountSearchForm();
        model.addAttribute("numberFormat", numberFormat);
        model.addAttribute("tradingAccountList",tradingAccountService.findAll());
        model.addAttribute("searchForm",searchForm);
        model.addAttribute("tradingAccountStopList",tradingAccountService.findAccountStopDeal());
        model.addAttribute("userInfo", currentUser);
        return "customer/manage-account-balance-page";
    }

    @RequestMapping(value = "/searchAllAccount", method = RequestMethod.GET)
    public String searchTradingAccount(Model model, @Nullable @ModelAttribute("customerForm") CustomerSearchForm searchForm, Principal principal){
        if (searchForm == null){
            return "redirect:/customer/manageCustomerAccount";
        }
        if(searchForm.getStatus().equals("All")){
            CustomerSearchForm form = new CustomerSearchForm();
            if(searchForm.getAccountNumber()!=null){
                form.setAccountNumber(searchForm.getAccountNumber());
            }
            if(searchForm.getCustomerName()!=null){
                form.setCustomerName(searchForm.getCustomerName());
            }
            if(searchForm.getPhone() !=null){
                form.setPhone(searchForm.getPhone());
            }
            User currentUser = userService.getUserByUsername(principal.getName());
            model.addAttribute("listCustomers",customerProcessService.findCustomerByNumberPhoneStatus(form));

            model.addAttribute("userInfo", currentUser);
            return VIEW_TRADING_ACCOUNT_PAGE;
        }else {
            User currentUser = userService.getUserByUsername(principal.getName());
            model.addAttribute("listCustomers",customerProcessService.findCustomerByNumberPhoneStatus(searchForm));

            model.addAttribute("userInfo", currentUser);
            return VIEW_TRADING_ACCOUNT_PAGE;
        }

    }
    @GetMapping({"/openAccount/{id}"})
    public String showOpenAccountPage(@PathVariable(name="id") int id ,Model model,Principal principal) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Customer customer = customerProcessService.findCustomerByID(id);
        model.addAttribute("customer",customer);
        if(customer == null){
            return "redirect:/contract/manageCustomer";
        }else{
            if(!customer.getNumber().contains("003C")){
                customer.setBirthDate(format.format(customer.getIdentification().getBirthDate()));
                customer.setIssueDate(format.format(customer.getIdentification().getIssueDate()));
                SummaryMKTReport summaryMKTReport = marketingRepository.getBrokerByUserId(customer.getCustomerId());

                TradingAccount tradingAccount = new TradingAccount();
                tradingAccount.setCustomerID(id);
                if (summaryMKTReport != null) {
                    tradingAccount.setBrokerCode(summaryMKTReport.getValue().toString());
                    tradingAccount.setBrokerName(summaryMKTReport.getName());
                }
                model.addAttribute("tradingAccount",tradingAccount);
                List<BankAccount> bankAccountList = customer.getBankAccounts();
                User currentUser = userService.getUserByUsername(principal.getName());
                model.addAttribute("userInfo", currentUser);
                model.addAttribute("owner",bankAccountList.get(0).getOwnerName());
                model.addAttribute("bankAccount",bankAccountList);
                return OPEN_ACCOUNT_PAGE;
            }else{
                return "redirect:/contract/manageCustomer";
            }

        }

    }
    @GetMapping({"/contract/showCreateContract/{id}"})
    public String showContractCreatePage(@PathVariable(name="id") int id , Model model, Principal principal){
        Customer customer = customerProcessService.findCustomerByID(id);
        if(customer == null){
            return "redirect:/customer/manageCustomer";
        }else{
            if(!customer.getContractNumber().contains("GCL")){


            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            customer.setBirthDate(format.format(customer.getIdentification().getBirthDate()));
            customer.setIssueDate(format.format(customer.getIdentification().getIssueDate()));
            String strDate = customer.getCreateDate()+"";
            String[] createDate = strDate.split(" ");
            model.addAttribute("createDate",createDate[0]);
            SummaryMKTReport summaryMKTReport = marketingRepository.getBrokerByUserId(customer.getCustomerId());
            if (summaryMKTReport != null) {
                customer.getTradingAccount().setBrokerCode(summaryMKTReport.getValue().toString());
                customer.getTradingAccount().setBrokerName(summaryMKTReport.getName());
            }
            Contract contract = new Contract();
            contract.setCustomer(customer);
            contract.setId(contractService.getContractID());
            User currentUser = userService.getUserByUsername(principal.getName());
            model.addAttribute("userInfo", currentUser);
            model.addAttribute("contract",contract);
            model.addAttribute("customer",customer);
            model.addAttribute("customerID",customer.getCustomerId());
            }else{
                return "redirect:/customer/manageCustomer";
            }
        }


        return CREATE_CONTRACT_PAGE;
    }

    @PostMapping({"/createAccount"})
    public String createTradingAccount(@Valid @ModelAttribute("tradingAccount") TradingAccount tradingAccount,
                                       Model model,
                                       Principal principal,
                                       RedirectAttributes redirectAttributes)  {
        Customer customer =customerProcessService.findCustomerByID(tradingAccount.getCustomerID());
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("userInfo", currentUser);
        tradingAccount.setBrokerName(customer.getEmployee().getName());
        tradingAccount.setBrokerCode(customer.getEmployee().getId()+"");
        if(customer == null){
            return "redirect:/customer/waitingCustomer";
        }else{
            if(tradingAccount.getAccountNumber().length()<4){

                String url ="redirect:/customer/openAccount/"+customer.getCustomerId();
                redirectAttributes.addFlashAttribute("length","Mã tài khoản gồm 4 số ");
                model.addAttribute("tradingAccount",tradingAccount);
                return  url ;
            }else if(!tradingAccount.getAccountNumber().matches("[0-9]{4}")){
                String url ="redirect:/customer/openAccount/"+customer.getCustomerId();
                redirectAttributes.addFlashAttribute("number","Mã tài khoản là số  ");
                model.addAttribute("tradingAccount",tradingAccount);
                return  url ;
            }else if(tradingAccountService.findTradingAccountByID("003C"+tradingAccount.getBrokerCode()+tradingAccount.getAccountNumber()) != null){
                String url ="redirect:/customer/openAccount/"+customer.getCustomerId();
                redirectAttributes.addFlashAttribute("duplicate","Mã tài khoản này đã tồn tại");
                model.addAttribute("tradingAccount",tradingAccount);
                return  url ;
            }
            else{
                SummaryMKTReport summaryMKTReport = marketingRepository.getBrokerByUserId(customer.getCustomerId());
                if (summaryMKTReport != null) {
                    tradingAccount.setBrokerCode(summaryMKTReport.getValue().toString());
                    tradingAccount.setBrokerName(summaryMKTReport.getName());
                }
                tradingAccount.setAccountNumber("003C"+tradingAccount.getBrokerCode()+tradingAccount.getAccountNumber());
                customer.setLevel(new Level(LevelEnum.LEVEL_6.getValue()));
                redirectAttributes.addFlashAttribute("flag","showAlert");
                customerProcessService.createTradingAccount(tradingAccount,customer);
                return "redirect:/customer/waitingCustomer";
            }
        }
    }
    @GetMapping({"/activeAccount/{id}"})
    public String activateAccount(@PathVariable(name="id") int id,RedirectAttributes redirectAttributes) throws ParseException {
        Customer customer = customerProcessService.findCustomerByID(id);
        customerProcessService.activateTradingAccount(customer);
        redirectAttributes.addFlashAttribute("flag","showAlert");

        return "redirect:/customer/manageCustomerAccount";
    }

    @PostMapping({"/createContract"})
    public String createContract(RedirectAttributes redirectAttributes
            ,@ModelAttribute("contract") Contract contract
            ,@RequestParam("file") MultipartFile multipartFile) throws ParseException, IOException {
        Customer customer = customerProcessService.findCustomerByID(contract.getCustomer().getCustomerId());
        String result = "redirect:/customer/contract/showCreateContract/"+customer.getCustomerId();

        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        System.out.println(filename);
        if(!filename.contains(".pdf")){

            redirectAttributes.addFlashAttribute("fileType","Hợp đồng là file .pdf");

            return result;
        }

        if(customer == null){
            return "redirect:/customer/manageCustomer";
        }else {
            long fileSzie = multipartFile.getSize();
            System.out.println(fileSzie);
            if(multipartFile.getSize()<10000000) {
                contract.setId(contractService.getContractID());
                ContractFile contractFile = new ContractFile();
                contractFile.setName("HopDongKhachHang"+customer.getCustomerCode()+".pdf");
                contractFile.setContent(multipartFile.getBytes());
                contractFile.setSize(multipartFile.getSize());
                contractFile.setUploadTime(WebUtils.getSystemDate());
                contractFile.setActive(Status.ACTIVE);
                contract.setContractFile(contractFile);
                System.out.println("contract file : " + contractFile.toString());
                customerProcessService.createContract(contract, customer);
                redirectAttributes.addFlashAttribute("flag", "showAlert");
                return "redirect:/customer/manageCustomer";
            }else {
                redirectAttributes.addFlashAttribute("overSize","Kích thước của file quá lớn");
                return result;
            }
        }

    }

    @GetMapping({"/showUpdateAccountForm/{id}"})
    public String showUpdateAccountForm(@PathVariable(name="id") int id ,Model model,Principal principal){
        Customer customer = customerProcessService.findCustomerByID(id);
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("userInfo", currentUser);
        if(customer == null){
            return "redirect:/customer/manageCustomerAccount";
        }else {
            model.addAttribute("customer",customer);
            model.addAttribute("tradingAccount",customer.getTradingAccount());
            return "customer/update-account-page";
        }

    }

    @PostMapping({"/updateAccount"})
    public String updateTradingAccount(RedirectAttributes redirectAttributes,@ModelAttribute("tradingAccount") TradingAccount tradingAccount) throws ParseException {
        Customer customer =customerProcessService.findCustomerByID(tradingAccount.getCustomerID());
        if(customer == null){
            return "redirect:/customer/manageCustomerAccount";
        }else {
            customer.setNumber(customer.getCustomerCode());
            tradingAccount.setAccountName(customer.getCustomerName());
            tradingAccount.setCreateDate(customer.getTradingAccount().getCreateDate());
            tradingAccount.setUpdateDate(Date.valueOf(LocalDate.now()));
            tradingAccount.setUpdateType(tradingAccount.getStatus());

            tradingAccount.setCustomer(customer);
            customer.setTradingAccount(tradingAccount);

            redirectAttributes.addFlashAttribute("flag","showAlert");

            customerProcessService.saveCustomer(customer);
            return "redirect:/customer/manageCustomerAccount";
        }

    }

    @RequestMapping(value = "/searchBalanceAccount", method = RequestMethod.GET)
    public String search(Model model, @Nullable @ModelAttribute("searchForm") TradingAccountSearchForm searchForm, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("userInfo", currentUser);

        double balance  = 0 ;
        if (searchForm == null){
            return "redirect:/tradingAccount/manageTradingAccount";
        }
        try{

            List<TradingAccount> tradingAccountList = tradingAccountService.findAllByNameNumberBalance(searchForm);
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(30);
            numberFormat.setGroupingUsed(true);
            model.addAttribute("numberFormat", numberFormat);
            model.addAttribute("tradingAccountList",tradingAccountService.findAllByNameNumberBalance(searchForm));
            model.addAttribute("tradingAccountStopList",tradingAccountService.findAccountStopDeal());
            return "customer/manage-account-balance-page";

        }catch (Exception e){
            if(e.getMessage().contains("Number")){
                model.addAttribute("balance","xin hay nhap so");
                return "redirect:/customer/manageAccountBalance";

            }
        }


        return "tradingAccount/manage-account-page";
    }
    @RequestMapping(value = "/account/balance/import", method = RequestMethod.POST)
    public String importExcelFile(RedirectAttributes redirectAttributes, @RequestParam("upload") MultipartFile file,
                                  Principal principal){
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Tập tin trống");
            return "redirect:/customer/manageAccountBalance";
        }
        ExcelReader excelReader = new ExcelReader();
        try {
            List<TradingAccount>  tradingAccounts = excelReader.getTradingAccountsBalance(file.getInputStream(), file.getOriginalFilename());
            for(TradingAccount tradingAccount: tradingAccounts){
                tradingAccountService.updateAccountBalance(tradingAccount.getAccountNumber(),tradingAccount.getBalance());
            }

            redirectAttributes.addFlashAttribute("flag","showAlert");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Không thể mở tệp đã chọn");
        } catch (IllegalStateException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (NotOfficeXmlFileException e){
            redirectAttributes.addFlashAttribute("error", "Tập tin đã chọn không đúng định dạng");
        }
        return "redirect:/customer/manageAccountBalance";
    }
    @GetMapping({"/account/showUpdateBalance/{id}"})
    public String showUpdateAccountBalance(@PathVariable(name="id") String id,Model model,Principal principal) throws ParseException {
        TradingAccount account = tradingAccountService.findTradingAccountByID(id);
        if(account == null){
            return "redirect:/tradingAccount/manageTradingAccount";
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("tradingAccount",account);
        model.addAttribute("userInfo", currentUser);
        return "customer/update-account-balance-page";
    }
    @PostMapping({"/account/updateAccountBalance"})
    public String updateAccountBalance(RedirectAttributes redirectAttributes,@ModelAttribute("tradingAccount") TradingAccount tradingAccount) throws ParseException {
        if(tradingAccount == null){
            return "redirect:/customer/manageAccountBalance";

        }else{
            tradingAccountService.updateAccountBalance(tradingAccount.getAccountNumber(),tradingAccount.getBalance());
            redirectAttributes.addFlashAttribute("flag","showAlert");

            return "redirect:/customer/manageAccountBalance";

        }



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
    public String showUpdatePage(@PathVariable(name="id") int id ,Model model, Principal principal){
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
        model.addAttribute("userName",principal.getName());

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
        return "redirect:/potential/home";
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
    @GetMapping({"/downloadContractFile/{id}"})
    public String downloadDocumentary(@PathVariable(name="id") int id, HttpServletResponse response) throws Exception {
        Customer customer = customerProcessService.findCustomerByID(id);
        ContractFile contractFile = customer.getContract().getContractFile();
        if(contractFile == null){
            return "redirect:/customer/viewContractCustomer";

        }else {
            customerProcessService.downloadContractFile(contractFile,response);


        }
        return "redirect:/documentary/home";

    }
}
