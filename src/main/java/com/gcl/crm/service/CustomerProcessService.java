package com.gcl.crm.service;

import com.gcl.crm.entity.Contract;
import com.gcl.crm.entity.Customer;
import com.gcl.crm.entity.TradingAccount;
import org.yaml.snakeyaml.constructor.SafeConstructor;

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

}
