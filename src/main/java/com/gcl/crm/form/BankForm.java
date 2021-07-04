package com.gcl.crm.form;

import javax.validation.constraints.NotBlank;

public class BankForm {

    @NotBlank(message = "Tên ngân hàng không thể bỏ trống")
    private String bankName;

    @NotBlank(message = "Số tài khoản không thể bỏ trống")
    private String bankNumber;

    @NotBlank(message = "Tên chủ khoản không thể bỏ trống")
    private String ownerBankingName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getOwnerBankingName() {
        return ownerBankingName;
    }

    public void setOwnerBankingName(String ownerBankingName) {
        this.ownerBankingName = ownerBankingName;
    }

}
