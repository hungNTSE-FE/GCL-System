package com.gcl.crm.form;

public class CustomerStatusEvaluationForm {
    private Integer stt;
    private String emp_name;
    private Integer num_of_registered_account;
    private Integer num_of_top_up_account;
    private Integer num_of_lot;

    public CustomerStatusEvaluationForm() {
    }

    public CustomerStatusEvaluationForm(String emp_name, Integer num_of_registered_account, Integer num_of_top_up_account) {
        this.emp_name = emp_name;
        this.num_of_registered_account = num_of_registered_account;
        this.num_of_top_up_account = num_of_top_up_account;
    }

    public CustomerStatusEvaluationForm(String emp_name, Integer num_of_registered_account, Integer num_of_top_up_account, Integer num_of_lot) {
        this.emp_name = emp_name;
        this.num_of_registered_account = num_of_registered_account;
        this.num_of_top_up_account = num_of_top_up_account;
        this.num_of_lot = num_of_lot;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public Integer getNum_of_registered_account() {
        return num_of_registered_account;
    }

    public void setNum_of_registered_account(Integer num_of_registered_account) {
        this.num_of_registered_account = num_of_registered_account;
    }

    public Integer getNum_of_top_up_account() {
        return num_of_top_up_account;
    }

    public void setNum_of_top_up_account(Integer num_of_top_up_account) {
        this.num_of_top_up_account = num_of_top_up_account;
    }

    public Integer getNum_of_lot() {
        return num_of_lot;
    }

    public void setNum_of_lot(Integer num_of_lot) {
        this.num_of_lot = num_of_lot;
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }
}
