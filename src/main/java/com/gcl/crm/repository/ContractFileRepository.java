package com.gcl.crm.repository;


import com.gcl.crm.entity.ContractFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractFileRepository extends JpaRepository<ContractFile,Integer> {

}
