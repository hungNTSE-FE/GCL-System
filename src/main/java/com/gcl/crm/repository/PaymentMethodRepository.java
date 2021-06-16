package com.gcl.crm.repository;


import com.gcl.crm.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {
}
