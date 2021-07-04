package com.gcl.crm.service;

import com.gcl.crm.entity.Task;
import com.gcl.crm.entity.Transaction;
import com.gcl.crm.repository.TaskRepository;
import com.gcl.crm.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findTransacionById(Long id) {
        Optional<Transaction> option = transactionRepository.findById(id);
        Transaction transaction = null ;
        if(option.isPresent()){
            transaction = option.get();
        }else {
            throw new RuntimeException("Task not found for id  :"+id);
        }
        return transaction;
    }
    @Override
    public void deleteTransactionByID(Long id) {
        this.transactionRepository.deleteById(id);

    }
}
