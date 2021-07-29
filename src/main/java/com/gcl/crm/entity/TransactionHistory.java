package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="transaction_history")
public class TransactionHistory {
    @Id
    @Column(name="transactionID")
    private String transactionID;

    @Column(name="account_number")
    private  String accountNumber;

    @Column(name="account_name")
    private String accountName;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="transaction_type")
    private String transactionType ;

    @Column(name="lot")
    private int lot ;

    @Column(name="money")
    private double money;






}
