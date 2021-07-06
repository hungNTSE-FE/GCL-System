package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Table(name="trading_account")
public class TradingAccount {

    @Id
    @Column(name = "account_number", length = 20)
    private String accountNumber;

    @Column(name="balance")
    private double balance;

    @Column(name="status")
    private String status;

    @Column(name="account_name")
    private String accountName ;


    @OneToOne(mappedBy = "tradingAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;
    public TradingAccount(){};

    public TradingAccount(String accountNumber, double balance, String status, String accountName, String brokerCode, String brokerName, Date createDate) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.accountName = accountName;
        this.brokerCode = brokerCode;
        this.brokerName = brokerName;
        this.createDate = createDate;
    }

    @Column(name="broker_code")
    private String brokerCode;
    @Column(name="broker_name")
    private  String brokerName ;
    @Column(name="create_date")
    private Date createDate;


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


}