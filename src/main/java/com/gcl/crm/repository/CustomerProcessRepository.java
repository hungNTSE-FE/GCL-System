package com.gcl.crm.repository;


import com.gcl.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerProcessRepository  extends JpaRepository<Customer,Integer> {
    @Query("SELECT new Customer (c.customerId, c.customerName, c.phoneNumber, c.email, c.number, c.contractNumber, c.source) FROM Customer c WHERE  c.contractNumber like %?1% ")
    List<Customer> getAllPotentialCustomer(String contractID);


}
