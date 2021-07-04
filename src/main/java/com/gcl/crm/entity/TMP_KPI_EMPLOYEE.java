package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "TMP_KPI_EMPLOYEE")
public class TMP_KPI_EMPLOYEE {
    @Column(name = "EMPLOYEE_ID")
    @Id
    private Long employeeId;

    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;

    @Column(name = "SUM_CUS_DATA")
    private Integer numCustomerData;

    @Column(name = "SUM_LOT")
    private Integer numLot;

    @Column(name = "KPI")
    private Double KPI;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getNumCustomerData() {
        return numCustomerData;
    }

    public void setNumCustomerData(Integer numCustomerData) {
        this.numCustomerData = numCustomerData;
    }

    public Integer getNumLot() {
        return numLot;
    }

    public void setNumLot(Integer numLot) {
        this.numLot = numLot;
    }

    public Double getKPI() {
        return KPI;
    }

    public void setKPI(Double KPI) {
        this.KPI = KPI;
    }
}
