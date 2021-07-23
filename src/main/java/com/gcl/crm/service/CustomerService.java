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
import java.time.LocalDate;
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
    PotentialService potentialService;

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

    public CustomerForm initForm(Long potentialId) {
        CustomerForm form = new CustomerForm();
        List<Employee> employeeList = employeeService.getAllWorkingEmployees();
        form.setEmployeeList(employeeList);
        form.setComboboxForm(initComboboxData());
        if (Objects.nonNull(potentialId)) {
            Potential potential = potentialService.getPotentialById(potentialId);
            form.setCustomerName(potential.getName());
            form.setEmail(potential.getEmail());
            form.setPhoneNumber(potential.getPhoneNumber());
            form.setHdnSourceId(potential.getSource().getSourceId());

        }
        return form;
    }

    @Transactional
    public void registerCustomer(CustomerForm customerForm) {
        try {
            Customer customer = convertToCustomerEntity(customerForm);
            customer.setEmployee(new Employee(customerForm.getHdnEmployeeId()));
            customer.setCustomerCode(customerForm.getCustomerName());
            // Set level 6 as default of customer when register customer successfully
            customer.setLevel(new Level(LevelEnum.LEVEL_6.getValue()));
            //Identification
            //kh-ng depar-docu
            customer.setNumber("none");
            customer.setContractNumber("none");
            BankAccount bankAccount = registerBanking(customerForm);
            bankAccount.setCustomer(customer);
            List<BankAccount> bankAccountList = new ArrayList<>();
             bankAccountList.add(bankAccount);
            customer.setIdentification(registerIdentification(customerForm));
            customer.setBankAccounts(bankAccountList);


            customerRepository.register(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public Employee getEmployeeByUser(User user) {
        return employeeRepository.findEmployeeByUser(user);
    }

    private Customer convertToCustomerEntity(CustomerForm customerForm) throws ParseException {
        Customer customer = new Customer();
        customer.setCustomerCode(null);
        customer.setCustomerName(customerForm.getCustomerName());
        customer.setAddress(customerForm.getAddress());
        customer.setEmail(customerForm.getEmail());
        customer.setGender(Gender.findByOption(customerForm.getGender()));
        customer.setSource(new Source(customerForm.getHdnSourceId()));
        customer.setPhoneNumber(customerForm.getPhoneNumber());
        customer.setDescription(customerForm.getDescription());
        customer.setStatus(customerForm.getStatus());
        customer.setCreateDate(java.sql.Date.valueOf(LocalDate.now()));
        customer.setUpdDate(java.sql.Date.valueOf(LocalDate.now()));
        return customer;
    }

    private Date convertStringToDate(String date, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern)
                .parse(date);
    }


}
