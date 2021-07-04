package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;
    @Column(name="name")
    private String name ;
    @Column(name="descpription")
    private String description ;
    @Column(name="contact")
    private String contact;
    @Column(name="manager")
    private String manager ;
    @Column(name="approveDate")
    private Date approveDate ;
    @Column(name="requestDate")
    private Date requestDate ;
    @Column(name="status")
    private String status ;
    @Column(name = "result")
    private String result ;
    @Column(name="transaction_type")
    private String transactionType ;
    @Column(name="userName")
    private String userName ;
    @Column(name="priority")
    private String priority;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "trading_account_number")
    private String tradingAccountNumber;

    public Long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTradingAccountNumber() {
        return tradingAccountNumber;
    }

    public void setTradingAccountNumber(String tradingAccountNumber) {
        this.tradingAccountNumber = tradingAccountNumber;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
