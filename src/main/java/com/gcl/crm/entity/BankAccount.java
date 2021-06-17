package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @Column(name = "account_number")
    private String id;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "OWNER_NAME")
    private String ownerName;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    private Date updDate;

    @Column(name = "ADD_USER")
    private String addUser;

    @Column(name = "UPD_USER")
    private String updUser;

    public BankAccount() {
    }

    public BankAccount(String id, String bankName, String ownerName) {
        this.id = id;
        this.bankName = bankName;
        this.ownerName = ownerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }
}
