package com.gcl.crm.repository;


import com.gcl.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProcessRepository  extends JpaRepository<Customer,Long> {


}
