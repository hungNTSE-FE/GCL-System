package com.gcl.crm.service;

import com.gcl.crm.entity.Contract;

import com.gcl.crm.entity.Task;
import com.gcl.crm.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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

    @Override
    public int cowContractRowData() {
        return contractRepository.countContractById()+1;
    }

    @Override
    public String getContractID() {
        String company = "GCL";
        String city = "HCM";
        String contractID = cowContractRowData()+"";
        String numberConcat ;
        while(contractID.length()<5){
            numberConcat = "0";
            contractID = numberConcat.concat(contractID);
        }
        String year = Calendar.getInstance().get(Calendar.YEAR)+"";
        return  company+contractID+city+year;
    }
}
