package com.gcl.crm.repository;

import com.gcl.crm.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomerRepository {

    @Autowired
    EntityManager entityManager;

    public Customer register(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

    

    public void update(Customer customer) { entityManager.merge(customer); }

}
