package com.gcl.crm.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CUSTOMER_DISTRIBUTION")
public class CustomerDistribution {

    @Column(name = "SEQ_NO")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer seq_no;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private MarketingGroup marketingGroup;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Potential potential;

    @Column(name = "DATE_DISTRIBUTION")
    private Date date_distribution;

    @Column(name = "ADD_DATE")
    private Date add_date;

    @Column(name = "ADD_USER")
    private Long add_user;

    @Column(name = "UPD_DATE")
    private Date upd_date;

    @Column(name = "UPD_USER")
    private Long upd_user;

    public Integer getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(Integer seq_no) {
        this.seq_no = seq_no;
    }

    public Date getDate_distribution() {
        return date_distribution;
    }

    public void setDate_distribution(Date date_distribution) {
        this.date_distribution = date_distribution;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public Date getUpd_date() {
        return upd_date;
    }

    public void setUpd_date(Date upd_date) {
        this.upd_date = upd_date;
    }

    public Long getUpd_user() {
        return upd_user;
    }

    public void setUpd_user(Long upd_user) {
        this.upd_user = upd_user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public MarketingGroup getMarketingGroup() {
        return marketingGroup;
    }

    public void setMarketingGroup(MarketingGroup marketingGroup) {
        this.marketingGroup = marketingGroup;
    }

    public Potential getPotential() {
        return potential;
    }

    public void setPotential(Potential potential) {
        this.potential = potential;
    }

    public Long getAdd_user() {
        return add_user;
    }

    public void setAdd_user(Long add_user) {
        this.add_user = add_user;
    }
}
