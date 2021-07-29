package com.gcl.crm.service;

import com.gcl.crm.dto.SelectItem;
import com.gcl.crm.entity.*;
import com.gcl.crm.enums.Status;
import com.gcl.crm.form.*;
import com.gcl.crm.repository.*;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MarketingServices {

    @Autowired
    MarketingRepository marketingRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PotentialRepository2 potentialRepository2;

    @Autowired
    SourceRepository sourceRepository;

    @Autowired
    MarketingGroupRepository marketingGroupRepository;

    @Autowired
    CustomerDistributionRepository customerDistributionRepository;

    @Autowired
    EmployeeService employeeService;

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

    public List<User> getListUser(){
        return userRepository.findAllByEnabled(true);
    }

    public ComboboxForm initComboboxData() {
        ComboboxForm comboboxForm = new ComboboxForm();
        List<SelectItem> sourceList = sourceRepository.getAll()
                .stream()
                .map(source -> new SelectItem(
                        source.getSourceId().toString(), source.getSourceName()))
                .collect(Collectors.toList());

        List<SelectItem> brokerNameList = employeeService.getAllWorkingEmployees()
                .stream()
                .map(employee -> new SelectItem(employee.getId().toString(), employee.getName()))
                .collect(Collectors.toList());

        comboboxForm.setListBrokerName(brokerNameList);
        comboboxForm.setListSource(sourceList);
        return comboboxForm;
    }

    @Transactional
    public List<TMP_KPI_EMPLOYEE> getKPIEmployeeReport(String fromDate, String toDate) {
        return marketingRepository.getKPIEmployeeReport(fromDate, toDate);
    }

    @Transactional
    public void distributeCustomerData(CustomerDistributionForm customerDistributionForm, User user) {
        try{
            List<Long> mktIdList = customerDistributionForm.getMktIdList();
            List<Long> potentialIDList = customerDistributionForm.getPotentialIdList();
            Date systemDate = WebUtils.getSystemDate();
            int i = 0;
            for (Long potentialId : potentialIDList) {
                CustomerDistribution customerDistribution = new CustomerDistribution();
                customerDistribution.setPotential(potentialRepository2.getReferenceById(potentialId));
                customerDistribution.setMarketingGroup(marketingGroupRepository.findByIdAndStatus(mktIdList.get(i++), Status.ACTIVE));
                customerDistribution.setAdd_date(systemDate);
                customerDistribution.setDate_distribution(systemDate);
                customerDistribution.setCustomer(null);
                customerDistribution.setUpd_date(systemDate);
                customerDistribution.setAdd_user(user.getEmployee().getId());
                customerDistribution.setUpd_user(user.getEmployee().getId());
                customerDistributionRepository.insertDataCustomer(customerDistribution);
                if (i == mktIdList.size()) i = 0;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
