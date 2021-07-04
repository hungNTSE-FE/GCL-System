package com.gcl.crm.form;

import com.gcl.crm.entity.Employee;
import com.gcl.crm.entity.Potential;

import java.util.List;

public class CustomerDistributionForm {
    List<Long> empIdList;
    List<Long> potentialIdList;

    public List<Long> getEmpIdList() {
        return empIdList;
    }

    public void setEmpIdList(List<Long> empIdList) {
        this.empIdList = empIdList;
    }

    public List<Long> getPotentialIdList() {
        return potentialIdList;
    }

    public void setPotentialIdList(List<Long> potentialIdList) {
        this.potentialIdList = potentialIdList;
    }
}
