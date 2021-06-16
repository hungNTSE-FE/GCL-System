package com.gcl.crm.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name="account_type",nullable = false)
    private String accountType;

    @Column(name="option_id",nullable = false)
    private String optionID;

    @Column(name="customer_name",nullable = false)
    private String customerName;

    @Column(name="birthday")
    private Date birthday ;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name="phone_number",nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PaymentMethod> paymentMethods;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "contract")




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getOptionID() {
        return optionID;
    }

    public void setOptionID(String optionID) {
        this.optionID = optionID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
