package com.gcl.crm.service;

import com.gcl.crm.entity.*;
import com.gcl.crm.form.CustomerForm;
import com.gcl.crm.form.CustomerSearchForm;
import com.gcl.crm.repository.CustomerProcessRepository;
import com.gcl.crm.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerProcessServiceImpl implements CustomerProcessService{
    @Autowired
    CustomerProcessRepository customerProcessRepository;
    @Override
    public List<Customer> getAllCustomer() {
        return customerProcessRepository.findAll();
    }
    @Autowired ContractService contractService;
    @Override
    public Customer findCustomerByID(int id) {
        List<Customer> customerList = getAllCustomer();
        for(int i = 0 ; i < customerList.size();i++){
            if(customerList.get(i).getCustomerId().equals(id)){

                return customerList.get(i);
            }
        }
        return null;
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerProcessRepository.save(customer);
    }

    @Override
    public List<Customer> getCustomerHaveTradingAccount() {
       return customerProcessRepository.getAllAccount("none");
    }

    @Override
    public void createTradingAccount(TradingAccount tradingAccount,Customer customer) {
        customer.setNumber(tradingAccount.getAccountNumber());
        customer.setCustomerCode(tradingAccount.getAccountNumber());
        tradingAccount.setCreateDate(Date.valueOf(LocalDate.now()));
        tradingAccount.setUpdateDate(Date.valueOf(LocalDate.now()));
        tradingAccount.setUpdateType("Inactive");
        tradingAccount.setAccountNumber(customer.getNumber());
        tradingAccount.setAccountName(customer.getCustomerName());
        tradingAccount.setCustomer(customer);
        customer.setTradingAccount(tradingAccount);
        customerProcessRepository.save(customer);
    }

    @Override
    public void createContract(Contract contract, Customer customer) {
        contract.setId(contractService.getContractID());
        contract.setCreateDate(Date.valueOf(LocalDate.now()));
        contract.setAccount_name(customer.getTradingAccount().getAccountName());
        contract.setBroker_name(customer.getTradingAccount().getBrokerName());
        contract.setBrokerCode(customer.getTradingAccount().getBrokerCode());
        contract.setNumber(customer.getTradingAccount().getAccountNumber());
        contract.setId(contract.getId());
        customer.setContractNumber(contract.getId());
        contract.setCustomer(customer);
        customer.setContract(contract);

        customerProcessRepository.save(customer);
    }

    @Override
    public void activateTradingAccount(Customer customer) {
        TradingAccount tradingAccount = customer.getTradingAccount();
        tradingAccount.setStatus("Active");
        tradingAccount.setUpdateType("Active");
        tradingAccount.setUpdateDate(Date.valueOf(LocalDate.now()));
        tradingAccount.setActiveDate(Date.valueOf(LocalDate.now()));
        System.out.println(tradingAccount.getActiveDate());
        tradingAccount.setCustomer(customer);
        customer.setTradingAccount(tradingAccount);

        customerProcessRepository.save(customer);
    }

    @Override
    public List<Customer> getAllContractCustomer() {
        return customerProcessRepository.getAllPotentialCustomer("GCL");

    }

    @Override
    public void saveAvatar(MultipartFile multipartFile, Customer customer) {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = "customerIdentification/"+customer.getCustomerId();
        try {
            FileUploadUtils.saveFile(uploadDir, filename, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> getWaitingCustomer() {
        return customerProcessRepository.getWaitingCustomer("none");
    }

    @Override
    public List<Customer> getCustomerNotContract() {
        return  customerProcessRepository.getCustomerNotContract("none","none");
    }

    @Override
    public List<Customer> findCustomerByNumberPhoneStatus(CustomerSearchForm customerSearchForm) {
        return customerProcessRepository.searchAccountByNumberNamePhoneStatus(customerSearchForm.getStatus(),customerSearchForm.getPhone(),customerSearchForm.getAccountNumber(),customerSearchForm.getCustomerName());
    }

    @Override
    public List<Customer> findWaitingCustomer(CustomerSearchForm customerSearchForm) {
        return customerProcessRepository.searchWaitingCustomer(customerSearchForm.getCustomerName(),customerSearchForm.getPhone(),customerSearchForm.getEmail());
    }

    @Override
    public List<Customer> findWaitingContractCustomer(CustomerSearchForm customerForm) {
        return customerProcessRepository.searchCustomerNotContract(customerForm.getCustomerName()
                ,customerForm.getEmail(),customerForm.getPhone(),
                customerForm.getAccountNumber(),"none","none");
    }


}
