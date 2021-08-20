package com.gcl.crm.repository;


import com.gcl.crm.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("select count(c.id) from Contract c")
    int countContractById();
    List<Contract> findAllByStatus(String status);
}
