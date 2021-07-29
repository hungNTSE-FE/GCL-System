package com.gcl.crm.repository;

import com.gcl.crm.entity.BankAccount;
import com.gcl.crm.entity.Customer;
import com.gcl.crm.entity.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BankRepository {

    @Autowired
    EntityManager entityManager;

    public void register(BankAccount bankAccount) {
        entityManager.persist(bankAccount);
    }

    public void update(BankAccount bankAccount) { entityManager.merge(bankAccount); }

    public BankAccount findObjectByPrimaryKey(String id) {
        return entityManager.find(BankAccount.class, id);
    }
}
