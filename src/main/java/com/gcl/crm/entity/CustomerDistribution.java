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

    @Column(name = "CUSTOMER_CODE")
    private Long customer_code;

    @Column(name = "EMPLOYEE_ID")
    private Long employee_id;

    @Column(name = "DATE_DISTRIBUTION")
    private Date date_distribution;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "ADD_DATE")
    private Date add_date;

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

    public Long getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(Long customer_code) {
        this.customer_code = customer_code;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public Date getDate_distribution() {
        return date_distribution;
    }

    public void setDate_distribution(Date date_distribution) {
        this.date_distribution = date_distribution;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
}
