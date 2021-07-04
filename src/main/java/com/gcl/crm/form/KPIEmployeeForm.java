package com.gcl.crm.form;

import com.gcl.crm.entity.TMP_KPI_EMPLOYEE;

import java.util.List;

public class KPIEmployeeForm {
    private String startDate;
    private String endDate;
    private List<TMP_KPI_EMPLOYEE> tmpKpiEmployeeList;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<TMP_KPI_EMPLOYEE> getTmpKpiEmployeeList() {
        return tmpKpiEmployeeList;
    }

    public void setTmpKpiEmployeeList(List<TMP_KPI_EMPLOYEE> tmpKpiEmployeeList) {
        this.tmpKpiEmployeeList = tmpKpiEmployeeList;
    }
}
