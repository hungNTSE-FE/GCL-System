package com.gcl.crm.service;

import com.gcl.crm.entity.Contract;
import com.gcl.crm.entity.Customer;
import com.gcl.crm.entity.Task;
import com.gcl.crm.entity.TradingAccount;
import com.gcl.crm.repository.CustomerProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerProcessServiceImpl implements CustomerProcessService{
    @Autowired
    CustomerProcessRepository customerProcessRepository;
    @Override
    public List<Customer> getAllCustomer() {
        return customerProcessRepository.findAll();
    }
    @Autowired ContractService contractService;
    @Override
    public Customer findCustomerByID(int id) {
        List<Customer> customerList = getAllCustomer();
        for(int i = 0 ; i < customerList.size();i++){
            if(customerList.get(i).getCustomerId().equals(id)){

                return customerList.get(i);
            }
        }
        return null;
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerProcessRepository.save(customer);
    }

    @Override
    public List<Customer> getCustomerHaveTradingAccount() {
        List<Customer> customerList = getAllCustomer();
        List<Customer> result = new ArrayList<>();
        for(int i = 0 ; i < customerList.size();i++){
            if(!customerList.get(i).getNumber().equals("none")){
                result.add(customerList.get(i));
            }
        }
        return  result;
    }

    @Override
    public void createTradingAccount(TradingAccount tradingAccount,Customer customer) {
        customer.setNumber(tradingAccount.getAccountNumber());
        customer.setCustomerCode(tradingAccount.getAccountNumber());
        tradingAccount.setCreateDate(Date.valueOf(LocalDate.now()));
        tradingAccount.setUpdateDate(Date.valueOf(LocalDate.now()));
        tradingAccount.setBrokerName(customer.getEmployee().getName());
        tradingAccount.setBrokerCode(customer.getEmployee().getId()+"");
        tradingAccount.setUpdateType("Inactive");
        tradingAccount.setAccountNumber(customer.getNumber());
        tradingAccount.setAccountName(customer.getCustomerName());
        tradingAccount.setCustomer(customer);
        customer.setTradingAccount(tradingAccount);
        customerProcessRepository.save(customer);
    }

    @Override
    public void createContract(Contract contract, Customer customer) {
        contract.setId(contractService.getContractID());
        contract.setCreateDate(Date.valueOf(LocalDate.now()));
        contract.setAccount_name(customer.getTradingAccount().getAccountName());
        contract.setBroker_name(customer.getTradingAccount().getBrokerName());
        contract.setBrokerCode(customer.getTradingAccount().getBrokerCode());
        contract.setNumber(customer.getTradingAccount().getAccountNumber());
        contract.setId(contract.getId());
        customer.setContractNumber(contract.getId());
        contract.setCustomer(customer);
        customer.setContract(contract);

        customerProcessRepository.save(customer);
    }

    @Override
    public void activateTradingAccount(Customer customer) {
        TradingAccount tradingAccount = customer.getTradingAccount();
        tradingAccount.setStatus("Active");
        tradingAccount.setUpdateType("Active");
        tradingAccount.setUpdateDate(Date.valueOf(LocalDate.now()));
        tradingAccount.setActiveDate(Date.valueOf(LocalDate.now()));
        System.out.println(tradingAccount.getActiveDate());
        tradingAccount.setCustomer(customer);
        customer.setTradingAccount(tradingAccount);

        customerProcessRepository.save(customer);
    }

    @Override
    public List<Customer> getAllContractCustomer() {
        return customerProcessRepository.getAllPotentialCustomer("GCL");

    }


}
