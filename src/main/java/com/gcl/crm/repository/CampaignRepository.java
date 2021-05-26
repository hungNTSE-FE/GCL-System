package com.gcl.crm.repository;

import com.gcl.crm.entity.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CampaignRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Campaign findObjectByPrimaryKey( Long id ){
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
        try {
            entityManager.merge( entity );
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete( Campaign entity ){
        entityManager.remove( entity );
    }
}
