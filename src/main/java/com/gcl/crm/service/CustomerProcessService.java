package com.gcl.crm.service;

import com.gcl.crm.entity.Contract;
import com.gcl.crm.entity.ContractFile;
import com.gcl.crm.entity.Customer;
import com.gcl.crm.entity.TradingAccount;
import com.gcl.crm.form.CustomerSearchForm;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface CustomerProcessService {
    List<Customer> getAllCustomer();
    Customer findCustomerByID(int id);
    void saveCustomer(Customer customer);
    List<Customer> getCustomerHaveTradingAccount();
    void createTradingAccount(TradingAccount tradingAccount, Customer customer);
    void createContract(Contract contract, Customer customer);
    void activateTradingAccount(Customer customer);
    List<Customer> getAllContractCustomer() ;
    void saveAvatar(MultipartFile multipartFile, Customer customer);
    List<Customer> getWaitingCustomer();
    List<Customer> getCustomerWaitingContract();
    List<Customer> findCustomerByNumberPhoneStatus(CustomerSearchForm customerSearchForm);
    List<Customer> findWaitingCustomer(CustomerSearchForm customerSearchForm);
    List<Customer> findWaitingContractCustomer(CustomerSearchForm customerForm);
    void downloadContractFile(ContractFile contractFile, HttpServletResponse response) throws IOException;
}
