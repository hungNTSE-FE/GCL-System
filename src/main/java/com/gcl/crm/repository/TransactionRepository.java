package com.gcl.crm.repository;

import com.gcl.crm.entity.Task;
import com.gcl.crm.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
