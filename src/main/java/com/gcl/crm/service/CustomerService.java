package com.gcl.crm.service;

import com.gcl.crm.dto.SelectItem;
import com.gcl.crm.entity.*;
import com.gcl.crm.enums.Gender;
import com.gcl.crm.enums.LevelEnum;
import com.gcl.crm.enums.Status;
import com.gcl.crm.form.ComboboxForm;
import com.gcl.crm.form.CustomerForm;
import com.gcl.crm.repository.BankRepository;
import com.gcl.crm.repository.CustomerRepository;
import com.gcl.crm.repository.EmployeeRepository;
import com.gcl.crm.repository.SourceRepository;
import com.gcl.crm.utils.WebUtils;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            customer.setEmployee(new Employee(2L));

            // Set level 6 as default of customer when register customer successfully
            customer.setLevel(new Level(LevelEnum.LEVEL_6.getValue()));

            // Register bank of customer
            registerBanking(customerForm);

            customerRepository.register(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Customer convertToCustomerEntity(CustomerForm customerForm) throws ParseException {
        Customer customer = new Customer();
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

    private Date convertStringToDate(String pattern, String date) throws ParseException {
        return new SimpleDateFormat(pattern)
                .parse(date);
    }

    private void registerBanking(CustomerForm customerForm) throws ParseException{
        BankAccount bankAccount = new BankAccount(customerForm.getBankNumber(),
                customerForm.getBankName(),
                customerForm.getOwnerBankingName());
        bankAccount.setCreateDate(WebUtils.getSystemDate());
        bankAccount.setUpdDate(WebUtils.getSystemDate());
        bankRepository.update(bankAccount);
    }

    private void registerIdentity(CustomerForm customerForm) throws ParseException{
        Identification identification = new Identification();
//            identification.setIdentityNumber = customerForm.getIdentifyNumber();
//            identification.setIssuePlace = customerForm.getPlaceOfIssue();
//            identification.setIssueDate = convertStringToDate("yyyy-MM-dd", customerForm.getDateOfIssue());
//            identification.setBirthDate = convertStringToDate("yyyy-MM-dd", customerForm.getDateOfBirth());
    }
}
