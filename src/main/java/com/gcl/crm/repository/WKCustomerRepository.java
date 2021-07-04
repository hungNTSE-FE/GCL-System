package com.gcl.crm.repository;

import com.gcl.crm.entity.WKCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class WKCustomerRepository {

    @Autowired
    EntityManager entityManager;

    public WKCustomer persitTmpCustomer(WKCustomer wkCustomer) {
        entityManager.persist(wkCustomer);
        return wkCustomer;
    }

    public WKCustomer mergeTmpCustomer(WKCustomer wkCustomer) {
        entityManager.merge(wkCustomer);
        return wkCustomer;
    }

    public List<WKCustomer> getAllWkCustomer(){
        String sql = "SELECT * FROM WK_CUSTOMER";
        return entityManager.createNativeQuery(sql, WKCustomer.class).getResultList();
    }
}
