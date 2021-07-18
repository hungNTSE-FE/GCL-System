package com.gcl.crm.service;

import com.gcl.crm.entity.Customer;
import com.gcl.crm.entity.Task;
import com.gcl.crm.repository.CustomerProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Customer findCustomerByID(String id) {
        List<Customer> customerList = getAllCustomer();
        for(int i = 0 ; i < customerList.size();i++){
            if(customerList.get(i).getCustomerCode().equals(id)){
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
    public void deleteCustomer(Long id) {
        customerProcessRepository.deleteById(id);
    }
}
