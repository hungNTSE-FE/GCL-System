package com.gcl.crm.repository;

import com.gcl.crm.entity.CustomerDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CustomerDistributionRepository {

    @Autowired
    EntityManager entityManager;

    public void insertDataCustomer(CustomerDistribution customerDistribution) {
        entityManager.merge(customerDistribution);
    }
}
