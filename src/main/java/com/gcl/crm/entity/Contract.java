package com.gcl.crm.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id", nullable = false)
    private Long id;




    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    @JoinColumn(name = "account_number", referencedColumnName = "account_number")
    private TradingAccount tradingAccount;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name="broker_code")
    private String brokerCode;
    @Column(name = "account_name")
    private String account_name;
    @Column(name="broker_name")
    private String broker_name;
    @Column(name="status")
    private String status;
    @Column(name="create_date")
    private Date createDate ;
    @OneToOne(mappedBy = "contract", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public TradingAccount getTradingAccount() {
        return tradingAccount;
    }

    public void setTradingAccount(TradingAccount tradingAccount) {
        this.tradingAccount = tradingAccount;
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBroker_name() {
        return broker_name;
    }

    public void setBroker_name(String broker_name) {
        this.broker_name = broker_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
