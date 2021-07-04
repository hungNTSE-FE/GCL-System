package com.gcl.crm.form;

public class CustomerStatusForm {
    private Integer stt;
    private String emp_name;
    private String level_0;
    private String level_1;
    private String level_2;
    private String level_3;
    private String level_4;
    private String level_5;
    private String level_6;
    private String level_7;

    public CustomerStatusForm() {
    }

    public CustomerStatusForm(String emp_name, String level_0, String level_1, String level_2,
                              String level_3, String level_4, String level_5, String level_6,
                              String level_7) {
        this.emp_name = emp_name;
        this.level_0 = level_0;
        this.level_1 = level_1;
        this.level_2 = level_2;
        this.level_3 = level_3;
        this.level_4 = level_4;
        this.level_5 = level_5;
        this.level_6 = level_6;
        this.level_7 = level_7;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getLevel_1() {
        return level_1;
    }

    public void setLevel_1(String level_1) {
        this.level_1 = level_1;
    }

    public String getLevel_2() {
        return level_2;
    }

    public void setLevel_2(String level_2) {
        this.level_2 = level_2;
    }

    public String getLevel_3() {
        return level_3;
    }

    public void setLevel_3(String level_3) {
        this.level_3 = level_3;
    }

    public String getLevel_4() {
        return level_4;
    }

    public void setLevel_4(String level_4) {
        this.level_4 = level_4;
    }

    public String getLevel_5() {
        return level_5;
    }

    public void setLevel_5(String level_5) {
        this.level_5 = level_5;
    }

    public String getLevel_6() {
        return level_6;
    }

    public void setLevel_6(String level_6) {
        this.level_6 = level_6;
    }

    public String getLevel_7() {
        return level_7;
    }

    public void setLevel_7(String level_7) {
        this.level_7 = level_7;
    }

    public String getLevel_0() {
        return level_0;
    }

    public void setLevel_0(String level_0) {
        this.level_0 = level_0;
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }
}
