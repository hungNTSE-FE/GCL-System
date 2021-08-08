package com.gcl.crm.entity;

import com.gcl.crm.form.CustomerStatusForm;
import com.gcl.crm.form.TradingAccountForm;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name="trading_account")
@SqlResultSetMapping(
        name = "getTradingAccountByMonth",
        classes = @ConstructorResult(
                targetClass = TradingAccountForm.class
                , columns = {
                @ColumnResult(name = "phone_number", type = String.class),
                @ColumnResult(name = "email", type = String.class),
                @ColumnResult(name = "account_number", type = String.class),
                @ColumnResult(name = "account_name", type = String.class),
                @ColumnResult(name = "broker_code", type = String.class),
                @ColumnResult(name = "broker_name", type = String.class),
        }
        )
)
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

    @Column (name="customerID")
    private int customerID ;

    @OneToOne(mappedBy = "tradingAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;

    public TradingAccount(){

    }

    public TradingAccount(String accountNumber, double balance, String accountName) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountName = accountName;
    }

    public TradingAccount(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

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
    @Column(name="active_date")
    private Date activeDate ;
    @Column (name="update_date")
    private Date updateDate;
    @Column(name = "update_type")
    private String updateType;

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }
}