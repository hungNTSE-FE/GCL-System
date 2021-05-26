package com.gcl.crm.repository;

import com.gcl.crm.entity.Source;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class SourceRepository {
    @PersistenceContext
    EntityManager entityManager;

    public Source findObjectByPrimaryKey(Long id ){
        return entityManager.find( Source.class, id );
    }

    public List< Source > getAll(){
        return entityManager.createQuery( "from " + Source.class.getName() )
                .getResultList();
    }

    public void save( Source entity ){
        entityManager.persist( entity );
    }

    public void update( Source entity ){
        try {
            entityManager.merge( entity );
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete( Source entity ){
        entityManager.remove( entity );
    }
}
