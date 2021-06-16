package com.gcl.crm.entity;

import javax.persistence.*;

@Entity
@Table
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name="bank_name",nullable = false)
    private String bankName;

    public Long getId() {
        return id;
    }

    public PaymentMethod() {
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
