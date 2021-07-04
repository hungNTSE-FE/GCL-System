package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "identification")
public class Identification {

    @Id
    @Column(name = "identity_number", length = 20)
    private String identityNumber;

    @Column(name = "issue_place")
    private String issuePlace;

    @Column(name = "front_image_url")
    private String frontImageUrl;

    @Column(name = "back_image_url")
    private String backImageUrl;

    @Column(name = "ethnic_group")
    private String ethnicGroup;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "date_of_birth")
    private Date birthDate;

    @Column(name = "permanent_place")
    private String permanentPlace;

    public Identification(String identityNumber, String issuePlace, String frontImageUrl, String backImageUrl, Date issueDate, Date birthDate) {
        this.identityNumber = identityNumber;
        this.issuePlace = issuePlace;
        this.frontImageUrl = frontImageUrl;
        this.backImageUrl = backImageUrl;
        this.issueDate = issueDate;
        this.birthDate = birthDate;
    }

    @OneToOne(mappedBy = "identification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Employee employee;
    @OneToOne(mappedBy = "identification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;

    public Identification() {

    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(String issuePlace) {
        this.issuePlace = issuePlace;
    }

    public String getFrontImageUrl() {
        return frontImageUrl;
    }

    public void setFrontImageUrl(String frontImageUrl) {
        this.frontImageUrl = frontImageUrl;
    }

    public String getBackImageUrl() {
        return backImageUrl;
    }

    public void setBackImageUrl(String backImageUrl) {
        this.backImageUrl = backImageUrl;
    }

    public String getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPermanentPlace() {
        return permanentPlace;
    }

    public void setPermanentPlace(String permanentPlace) {
        this.permanentPlace = permanentPlace;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
