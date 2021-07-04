package com.gcl.crm.service;

import com.gcl.crm.dto.SelectItem;
import com.gcl.crm.entity.*;
import com.gcl.crm.enums.Gender;
import com.gcl.crm.enums.LevelEnum;
import com.gcl.crm.form.ComboboxForm;
import com.gcl.crm.form.CustomerForm;
import com.gcl.crm.repository.*;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    SourceRepository sourceRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    IdentificationRepository identificationRepository;

    @Autowired
    WKCustomerRepository wkCustomerRepository;


    public ComboboxForm initComboboxData() {
        ComboboxForm comboboxForm = new ComboboxForm();
        List<SelectItem> sourceList = sourceRepository.getAll()
                                                .stream()
                                                .map(source -> new SelectItem(
                                                        source.getSourceId().toString(), source.getSourceName()))
                                                .collect(Collectors.toList());

        List<SelectItem> brokerNameList = employeeService.getAllWorkingEmployees()
                .stream()
                .map(employee -> new SelectItem(employee.getId().toString(), employee.getName()))
                .collect(Collectors.toList());

        comboboxForm.setListBrokerName(brokerNameList);
        comboboxForm.setListSource(sourceList);
        return comboboxForm;
    }

    @Transactional
    public void registerCustomer(CustomerForm customerForm) {
        try {
            Customer customer = convertToCustomerEntity(customerForm);
            customer.setEmployee(new Employee(1L));

            // Set level 6 as default of customer when register customer successfully
            customer.setLevel(new Level(LevelEnum.LEVEL_6.getValue()));
            //Identification

             List<BankAccount> bankAccountList = new ArrayList<>();
             bankAccountList.add(registerBanking(customerForm));

            customer.setIdentification(registerIdentification(customerForm));
            customer.setBankAccounts(bankAccountList);


            customerRepository.register(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public WKCustomer saveCustomer(CustomerForm customerForm) {
        WKCustomer wkCustomer = new WKCustomer();
        try {
            wkCustomer.setCustomerCode(customerForm.getHdnCustomerCode());
            wkCustomer.setCustomerName(customerForm.getCustomerName());
            wkCustomer.setAddress(customerForm.getAddress());
            wkCustomer.setEmail(customerForm.getEmail());
            wkCustomer.setGender(Gender.findByOption(customerForm.getGender()));
            wkCustomer.setPhoneNumber(customerForm.getPhoneNumber());
            wkCustomer.setDescription(customerForm.getDescription());
            wkCustomer.setStatus(customerForm.getStatus());
            wkCustomer.setCreateDate(WebUtils.getSystemDate());
            wkCustomer.setUpdDate(WebUtils.getSystemDate());

            wkCustomer.setIdentityNumber(customerForm.getIdentifyNumber());
            wkCustomer.setIssuePlace(customerForm.getPlaceOfIssue());
            wkCustomer.setFrontImageUrl(customerForm.getImageBefore());
            wkCustomer.setBackImageUrl(customerForm.getImageAfter());
            wkCustomer.setIssueDate(convertStringToDate(customerForm.getDateOfIssue(), "yyyy-mm-dd"));
            wkCustomer.setBirthDate(convertStringToDate(customerForm.getDateOfBirth(), "yyyy-mm-dd"));

            if (Objects.isNull(customerForm.getHdnCustomerCode())) {
                wkCustomerRepository.persitTmpCustomer(wkCustomer);
            } else {
                wkCustomerRepository.mergeTmpCustomer(wkCustomer);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return wkCustomer;
    }

    public BankAccount registerBanking(CustomerForm customerForm) throws ParseException{
        BankAccount bankAccount = new BankAccount(customerForm.getBankNumber(),
                customerForm.getBankName(),
                customerForm.getOwnerBankingName());
        bankAccount.setBalance(0);;

        bankAccount.setCreateDate(WebUtils.getSystemDate());
        bankAccount.setUpdDate(WebUtils.getSystemDate());
        return  bankAccount;
    }

    public Identification registerIdentification(CustomerForm customerForm) throws ParseException{
        Identification identification = new Identification(customerForm.getIdentifyNumber(),
                customerForm.getPlaceOfIssue(),
                customerForm.getImageBefore(),
                customerForm.getImageAfter(),
                convertStringToDate(customerForm.getDateOfIssue(), "yyyy-mm-dd"),
                convertStringToDate(customerForm.getDateOfBirth(), "yyyy-mm-dd"));

        identification.setEthnicGroup("");
        identification.setPermanentPlace("");
        return  identification;

    }

    private Customer convertToCustomerEntity(CustomerForm customerForm) throws ParseException {
        Customer customer = new Customer();
        customer.setCustomerCode(customerForm.getHdnCustomerCode());
        customer.setCustomerName(customerForm.getCustomerName());
        customer.setAddress(customerForm.getAddress());
        customer.setEmail(customerForm.getEmail());
        customer.setGender(Gender.findByOption(customerForm.getGender()));
        customer.setPhoneNumber(customerForm.getPhoneNumber());
        customer.setDescription(customerForm.getDescription());
        customer.setStatus(customerForm.getStatus());
        customer.setCreateDate(WebUtils.getSystemDate());
        customer.setUpdDate(WebUtils.getSystemDate());
        return customer;
    }

    private Date convertStringToDate(String date, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern)
                .parse(date);
    }


}
