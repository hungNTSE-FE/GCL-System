package com.gcl.crm.repository;

import com.gcl.crm.entity.Documentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentaryRepository extends JpaRepository<Documentary,Integer> {

    @Query("SELECT new Documentary(d.id, d.name, d.size) FROM Documentary d ORDER BY d.uploadTime DESC")
    List<Documentary> findAll();
}
