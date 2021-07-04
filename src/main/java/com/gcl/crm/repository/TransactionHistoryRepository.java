package com.gcl.crm.repository;

import com.gcl.crm.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory,Long> {
}
