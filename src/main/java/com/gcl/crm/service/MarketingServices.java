package com.gcl.crm.service;

import com.gcl.crm.form.CustomerStatusEvaluationForm;
import com.gcl.crm.form.CustomerStatusForm;
import com.gcl.crm.form.CustomerStatusReportForm;
import com.gcl.crm.repository.CustomerRepository;
import com.gcl.crm.repository.MarketingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MarketingServices {

    @Autowired
    MarketingRepository marketingRepository;

    @Autowired
    CustomerRepository customerRepository;

    public void initScreen() {
    }

    public CustomerStatusReportForm getCustomerStatusReport(String fromDate, String toDate) {
        List<CustomerStatusForm> customerStatusList = marketingRepository.getCustomerStatusList(fromDate, toDate);

        List<CustomerStatusEvaluationForm> customerStatusEvaluationList = marketingRepository
                                                                    .getCustomerStatusEvaluationList(fromDate, toDate);

        for (int i = 0; i < customerStatusList.size(); i++) {
            customerStatusList.get(i).setStt(i + 1);
        }
        for (int i = 0; i < customerStatusEvaluationList.size(); i++) {
            customerStatusEvaluationList.get(i).setStt(i + 1);
        }
        return new CustomerStatusReportForm(customerStatusEvaluationList, customerStatusList);
    }

    public Boolean validateDate(Date fromDate, Date toDate) {
        return Boolean.TRUE;
    }
}
