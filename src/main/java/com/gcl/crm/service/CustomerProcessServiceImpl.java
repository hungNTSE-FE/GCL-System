package com.gcl.crm.service;

import com.gcl.crm.entity.Customer;
import com.gcl.crm.entity.Task;
import com.gcl.crm.repository.CustomerProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Customer findCustomerByID(long id) {
        Optional<Customer> option = customerProcessRepository.findById(id);
        Customer customer = null ;
        if(option.isPresent()){
            customer = option.get();
        }else {
            throw new RuntimeException("Task not found for id  :"+id);
        }
        return customer;
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerProcessRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerProcessRepository.deleteById(id);
    }
}
