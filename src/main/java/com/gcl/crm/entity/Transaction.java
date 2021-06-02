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
    @Column(name="priority")
    private String priority;
}
