package com.gcl.crm.service;

import com.gcl.crm.entity.Contract;

import com.gcl.crm.entity.Task;
import com.gcl.crm.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService{
    @Autowired
    private ContractRepository contractRepository;
    @Override
    public List<Contract> getAllContract() {
        return contractRepository.findAll();
    }

    @Override
    public void createContract(Contract contract) {
        contractRepository.save(contract);
    }

    @Override
    public Contract findContractByID(Long id) {
        Optional<Contract> option = contractRepository.findById(id);
        Contract contract = null ;
        if(option.isPresent()){
            contract = option.get();
        }else {
            throw new RuntimeException("Task not found for id  :"+id);
        }
        return contract;
    }
}
