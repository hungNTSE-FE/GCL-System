package com.gcl.crm.service;

import com.gcl.crm.entity.Task;
import com.gcl.crm.entity.Transaction;

import java.util.List;

public interface TransactionService {
    void createTransaction(Transaction transaction);
    List<Transaction> getAllTransaction();
    Transaction findTransacionById(Long id);
    void deleteTransactionByID(Long id);
}
