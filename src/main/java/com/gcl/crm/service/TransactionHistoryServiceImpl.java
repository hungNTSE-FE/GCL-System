package com.gcl.crm.service;

import com.gcl.crm.entity.Potential;
import com.gcl.crm.entity.Source;
import com.gcl.crm.entity.TransactionHistory;
import com.gcl.crm.entity.User;
import com.gcl.crm.enums.Status;
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

    @Override
    public boolean importTransactionHistory(List<TransactionHistory> transactionHistories) {
        if (transactionHistories.size() == 0){
            return false;
        }

        for(int i = 0 ;  i<transactionHistories.size();i++){
            try{
                transactionHistoryRepository.save(transactionHistories.get(i));
                transactionHistories.remove(transactionHistories.get(i));
            }catch (Exception e){
                if(e.getMessage().contains("duplicate")){
                    transactionHistories.remove(transactionHistories.get(i));
                    importTransactionHistory(transactionHistories);
                }
            }
        }
        return true;
    }
}
