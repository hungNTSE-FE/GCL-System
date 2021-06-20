package com.gcl.crm.service;

import com.gcl.crm.entity.Customer;

import java.util.List;

public interface CustomerProcessService {
    List<Customer> getAllCustomer();
    Customer findCustomerByID(long id);
    void saveCustomer(Customer customer);


    void deleteCustomer(Long id);
}
