package com.gcl.crm.service;



import com.gcl.crm.entity.Contract;

import java.util.List;

public interface ContractService {

    List<Contract> getAllContract();
    void createContract(Contract contract);

    Contract findContractByID(Long id);
}
