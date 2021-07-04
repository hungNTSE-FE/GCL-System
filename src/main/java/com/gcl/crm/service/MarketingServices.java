package com.gcl.crm.service;

import com.gcl.crm.dto.SelectItem;
import com.gcl.crm.entity.*;
import com.gcl.crm.form.*;
import com.gcl.crm.repository.*;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MarketingServices {

    @Autowired
    MarketingRepository marketingRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository2 userRepository2;

    @Autowired
    WKCustomerRepository wkCustomerRepository;

    @Autowired
    PotentialRepository2 potentialRepository2;

    @Autowired
    SourceRepository sourceRepository;

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

    public List<WKCustomer> getAllWkCustomer(){
        return wkCustomerRepository.getAllWkCustomer();
    }

    public List<AppUser> getListUser(){
        return userRepository2.findAllByEnabled(true);
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
    public void distributeCustomerData(CustomerDistributionForm customerDistributionForm) {
        try{
            List<Long> empId = customerDistributionForm.getEmpIdList();
            List<Potential> potentialList = potentialRepository2.getListPotentialToShare();
            Random random = new Random();
            Date systemDate = WebUtils.getSystemDate();
            int i = 0;
            for (Potential potential : potentialList) {
                int randomIndex = random.nextInt(empId.size());
                CustomerDistribution customerDistribution = new CustomerDistribution();
                customerDistribution.setCustomer_code(potential.getId());
                customerDistribution.setEmployee_id(empId.get(i++));
                customerDistribution.setAdd_date(systemDate);
                customerDistribution.setDate_distribution(systemDate);
                customerDistribution.setUpd_date(systemDate);
                customerDistributionRepository.insertDataCustomer(customerDistribution);
                if (i == empId.size()) i = 0;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

}
