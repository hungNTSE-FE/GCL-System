package com.gcl.crm.repository;

import com.gcl.crm.entity.Campaign;
import com.gcl.crm.entity.UserRole;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CampaignRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Campaign findObjectByPrimaryKey( Integer id ){
        return entityManager.find( Campaign.class, id );
    }

    public List< Campaign > getAll(){
        return entityManager.createQuery( "from " + Campaign.class.getName() )
                .getResultList();
    }

    public void save( Campaign entity ){
        entityManager.persist( entity );
    }

    public void update( Campaign entity ){
        entityManager.merge( entity );
    }

    public boolean delete( Integer campaignCode ){
        entityManager.remove(findObjectByPrimaryKey(campaignCode));
        return true;
    }

}
