package com.gcl.crm.form;

import java.util.Collections;
import java.util.List;

public class CustomerStatusReportForm {
    List<CustomerStatusEvaluationForm> customerStatusEvaluationFormList;
    List<CustomerStatusForm> customerStatusFormList;

    public CustomerStatusReportForm() {
    }

    public CustomerStatusReportForm(List<CustomerStatusEvaluationForm> customerStatusEvaluationFormList, List<CustomerStatusForm> customerStatusFormList) {
        this.customerStatusEvaluationFormList = customerStatusEvaluationFormList;
        this.customerStatusFormList = customerStatusFormList;
    }

    public List<CustomerStatusEvaluationForm> getCustomerStatusEvaluationFormList() {
        return Collections.unmodifiableList(customerStatusEvaluationFormList);
    }

    public void setCustomerStatusEvaluationFormList(List<CustomerStatusEvaluationForm> customerStatusEvaluationFormList) {
        this.customerStatusEvaluationFormList = customerStatusEvaluationFormList;
    }

    public List<CustomerStatusForm> getCustomerStatusFormList() {
        return Collections.unmodifiableList(customerStatusFormList);
    }

    public void setCustomerStatusFormList(List<CustomerStatusForm> customerStatusFormList) {
        this.customerStatusFormList = customerStatusFormList;
    }
}
