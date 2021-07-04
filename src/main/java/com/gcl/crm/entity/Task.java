package com.gcl.crm.entity;

import com.gcl.crm.enums.Status;
import lombok.Data;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer task_id;

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


    @Column(name = "active", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status active;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    public Status getActive() {
        return active;
    }

    public String getDescription() {
        return description;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActive(Status active) {
        this.active = active;
    }

    public Task() {

    }

    public Integer getTask_id() {
        return task_id;
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
