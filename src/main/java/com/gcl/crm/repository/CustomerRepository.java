package com.gcl.crm.repository;

import com.gcl.crm.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomerRepository {

    @Autowired
    EntityManager entityManager;

    public void save(Customer customer) {
        entityManager.persist(customer);
    }

    public void update(Customer customer) { entityManager.merge(customer); }

}
