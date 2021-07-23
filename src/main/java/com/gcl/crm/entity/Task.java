package com.gcl.crm.entity;

import com.gcl.crm.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

import java.util.List;

@Entity
@Data
@Table(name="task")
public class    Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long task_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="complex")
    private String complex;

    @Column(name="priority")
    private String priority;

    @Column(name="startDate")
    private Date startDate ;
    @Column(name="endDate")
    private Date endDate ;
    @Column (name = "status")
    private String status;

    @Column(name="description")
    private String description ;
    @Column(name="departmentName")
    private String departmentName;
    @Column(name="createDate")
    private Date createDate;
    @Column(name="submitEmployee")
    private  Long employeeID ;
    @Column(name ="submitDate")
    private Date submitDate ;
    @Column(name="submitStatus")
    private String submitStatus;

    @Column(name="updateDate")
    private Date updateDate;

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public java.util.Date getCreateDate() {
        return createDate;
    }



    public java.util.Date getSubmitDate() {
        return submitDate;
    }



    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }



    public void setSumbitDate(Date sumbitDate) {
        this.submitDate = sumbitDate;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Column(name = "active", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status active;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="task_employee",joinColumns = @JoinColumn(name="task_id"),inverseJoinColumns = @JoinColumn(name="employee_id"))
    private List<Employee> employees;

    public Status getActive() {
        return active;
    }

    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(Status active) {
        this.active = active;
    }

    public Task() {

    }

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComplex() {
        return complex;
    }

    public void setComplex(String complex) {
        this.complex = complex;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }



    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }






}
