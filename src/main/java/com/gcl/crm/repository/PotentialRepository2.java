package com.gcl.crm.repository;

import com.gcl.crm.entity.Potential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PotentialRepository2 {

    @Autowired
    private EntityManager entityManager;

    public List<Potential> getListPotentialToShare() {
        String sql = "select\n" +
                "       *\n" +
                "from potential p1\n" +
                "where not exists(\n" +
                "    select 1\n" +
                "    from customer_distribution p2\n" +
                "    where p1.id = p2.customer_code\n" +
                "    )";
        Query query = entityManager.createNativeQuery(sql, Potential.class);
        return query.getResultList();
    }
}
