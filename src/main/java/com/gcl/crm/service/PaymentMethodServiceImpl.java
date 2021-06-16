package com.gcl.crm.service;

import com.gcl.crm.entity.PaymentMethod;
import com.gcl.crm.repository.PaymentMethodRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService{
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethod> getAllPaymentMethod() {
        return paymentMethodRepository.findAll();
    }
}
