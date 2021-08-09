package com.gcl.crm.repository;


import com.gcl.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerProcessRepository  extends JpaRepository<Customer,Integer> {
    @Query("SELECT new Customer (c.customerId, c.customerName, c.phoneNumber, c.email, c.number, c.contractNumber, c.source) FROM Customer c WHERE  c.contractNumber like %?1% ")
    List<Customer> getAllPotentialCustomer(String contractID);
    @Query("SELECT new Customer (c.customerId, c.customerName, c.phoneNumber, c.email, c.description) FROM Customer c WHERE  c.number like %?1% order by  c.createDate desc ")
    List<Customer> getWaitingCustomer(String accountNumber);
    @Query("SELECT new Customer (c.customerId, c.customerCode, c.customerName, c.phoneNumber, c.tradingAccount) FROM Customer c WHERE  c.number not like %?1% order by  c.createDate desc ")
    List<Customer> getAllAccount(String accountNumber);
    @Query("SELECT new Customer (c.customerId, c.customerCode, c.customerName, c.phoneNumber, c.tradingAccount) FROM Customer c WHERE  c.number not like %?1% order by  c.createDate desc ")
    List<Customer> getAllCustomer(String accountNumber);

    @Query("SELECT new Customer (c.customerId, c.customerName, c.phoneNumber, c.email, c.description,c.number) FROM Customer c WHERE  c.number not like %?1% and c.contractNumber like %?2% order by  c.createDate desc ")
    List<Customer> getCustomerNotContract(String accountNumber,String contractNumber);
    @Query("SELECT new Customer (c.customerId, c.customerCode, c.customerName, c.phoneNumber, c.tradingAccount) FROM Customer c WHERE  c.tradingAccount.status = ?1 and c.phoneNumber like %?2%  and c.number like %?3% and c.customerName like %?4% ")
    List<Customer> searchAccountByNumberNamePhoneStatus(String status,String phoneNumber,String number,String name);
    @Query("SELECT new Customer (c.customerId, c.customerName, c.phoneNumber, c.email, c.description) FROM Customer c WHERE c.customerName like %?1% and  c.phoneNumber like %?2% and c.email like %?3% order by  c.createDate desc ")
    List<Customer> searchWaitingCustomer(String name, String phone , String email );
    @Query("SELECT new Customer (c.customerId, c.customerName, c.phoneNumber, c.email, c.description,c.number) FROM Customer c WHERE  c.customerName like %?1% and c.email like %?2% and c.phoneNumber like %?3% and c.number like %?4% and c.number not like %?5% and c.contractNumber like %?6% order by  c.createDate desc ")
    List<Customer> searchCustomerNotContract(String customerName,String email,String phone,String account,String number,String contract);
}
