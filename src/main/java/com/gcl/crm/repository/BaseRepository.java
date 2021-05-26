package com.gcl.crm.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class BaseRepository<T extends Serializable> {

    private Class< T > clazz;

    @PersistenceContext
    EntityManager entityManager;

    public BaseRepository(){};

    public BaseRepository(Class< T > clazzToSet){
        this.clazz = clazzToSet;
    }

    public T findObjectByPrimaryKey( Long id ){
        return entityManager.find( clazz, id );
    }
    public List< T > getAll(){
        return entityManager.createQuery( "from " + clazz.getName() )
                .getResultList();
    }

    public void save( T entity ){
        entityManager.persist( entity );
    }

    public void update( T entity ){
        entityManager.merge( entity );
    }

    public void delete( T entity ){
        entityManager.remove( entity );
    }

}
