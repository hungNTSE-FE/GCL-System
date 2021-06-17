package com.gcl.crm.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TRADING_ACCOUNT")
public class Trading_Account {

    @Id
    @Column(name = "ACCOUNT_CODE")
    private String account_code;

    @Column(name = "ACCOUNT_NAME")
    private String account_name;

    @Column(name = "BROKER_CODE")
    private Long broker_code;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    public String getAccount_code() {
        return account_code;
    }

    public void setAccount_code(String account_code) {
        this.account_code = account_code;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public Long getBroker_code() {
        return broker_code;
    }

    public void setBroker_code(Long broker_code) {
        this.broker_code = broker_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
