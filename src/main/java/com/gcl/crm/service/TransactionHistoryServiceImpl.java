package com.gcl.crm.service;

import com.gcl.crm.entity.TransactionHistory;
import com.gcl.crm.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService{
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;
    @Override
    public List<TransactionHistory> getAllTransactionHistory() {
        return transactionHistoryRepository.findAll();
    }
}
