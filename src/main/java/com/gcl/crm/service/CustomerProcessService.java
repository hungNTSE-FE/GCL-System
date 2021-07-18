package com.gcl.crm.service;

import com.gcl.crm.entity.Customer;

import java.util.List;

public interface CustomerProcessService {
    List<Customer> getAllCustomer();
    Customer findCustomerByID(String id);
    void saveCustomer(Customer customer);
    List<Customer> getCustomerHaveTradingAccount();

    void deleteCustomer(Long id);
}
