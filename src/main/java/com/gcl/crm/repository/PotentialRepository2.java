package com.gcl.crm.repository;

import com.gcl.crm.entity.Potential;
import com.gcl.crm.form.PotentialSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

@Repository
public class PotentialRepository2 {

    @Autowired
    private EntityManager entityManager;

    public List<PotentialSearchForm> getListPotentialToShare(List<Long> listSelectedId) {
        String sql = "select p1.id as potentialID, coalesce(p1.name, '') as name, coalesce (p1.phone_number, '') as phone, " +
                "coalesce (p1.email,'') as email, coalesce (p1.date, '') as time, coalesce (s.source_name, '') as source_name\n" +
                "from potential p1\n" +
                "inner join\n" +
                "source s on p1.source_id = s.source_id\n" +
                "where not exists(\n" +
                "    select 1\n" +
                "    from customer_distribution p2\n" +
                "    where p1.id = p2.potential_id\n" +
                "    )\n";
        if (!CollectionUtils.isEmpty(listSelectedId)) {
            sql = sql + " and p1.id in :list_selected_id";
        }
        Query query = entityManager.createNativeQuery(sql, "getPotentailFormToShare");

        if (!CollectionUtils.isEmpty(listSelectedId)) {
            query.setParameter("list_selected_id", listSelectedId);
        }
        return query.getResultList();
    }

    public Potential getReferenceById(Long id) {
        return entityManager.getReference(Potential.class, id);
    }

    public List<Potential> getListPotentialOfSale(Long mkt_id) {
        String sql = "select potential.*\n" +
                "from potential\n" +
                "inner join (\n" +
                "    select potential_id\n" +
                "    from customer_distribution\n" +
                "    where id = :mkt_id\n" +
                "    ) tmp\n" +
                "on tmp.potential_id = potential.id";

        Query query = entityManager.createNativeQuery(sql, Potential.class);
        query.setParameter("mkt_id", mkt_id);
        return query.getResultList();
    }

    public void ratingPotential() {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("PROC_RATING_POTENTIAL");
        storedProcedureQuery.execute();
    }
}
