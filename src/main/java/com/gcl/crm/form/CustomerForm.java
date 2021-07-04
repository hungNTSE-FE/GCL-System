package com.gcl.crm.form;

import java.sql.Date;
import javax.validation.constraints.*;
public class CustomerForm {

    private Long hdnCustomerCode;

    @NotBlank(message = "Tên khách hàng không thẻ bỏ trống")
    @Size(min = 3, max = 50, message = "Độ dài tên khách hàng phải ở khoảng 3 đến 50 ký tự")
    private String customerName;

    @NotNull(message = "Giới tính không thể bỏ trống")
    private String gender;

    @NotBlank(message = "Địa chỉ không thể bỏ trống")
    private String address;

    @NotBlank(message = "Số điện thoại không thể bỏ trống")
    @Size(max = 10, message = "Độ dài số điện thoại không được vượt quá 10 kí tự")
    private String phoneNumber;

    @NotBlank(message = "Email không thể bỏ trống")
    @Email(message = "Vui lòng nhập đúng định dạng email")
    private String email;

    @NotBlank(message = "Trạng thái không thể bỏ trống")
    private String status;

    private String description;

    @NotBlank(message = "Tên ngân hàng không thể bỏ trống")
    private String bankName;

    @NotBlank(message = "Số tài khoản không thể bỏ trống")
    private String bankNumber;

    @NotBlank(message = "Tên chủ khoản không thể bỏ trống")
    private String ownerBankingName;

    @NotBlank(message = "Căn cước công dân không thể bỏ trống")
    @Size(max = 10)
    private String identifyNumber;

    @NotBlank(message = "Ngày cấp không thể bỏ trống")
    private String dateOfIssue;

    @NotBlank(message = "Nơi cấp không thể bỏ trống")
    private String placeOfIssue;

    @NotBlank(message = "Ngày sinh không thể bỏ trống")
    private String dateOfBirth;

    @NotBlank(message = "Tài khoản giao dịch không thể bỏ trống")
    private String accountCode;

    @NotBlank(message = "Tên tài khoản giao dịch không thể bỏ trống")
    private String accountName;

    @NotBlank(message = "Mã môi giới không thể bỏ trống")
    private String brokerCode;

    private String imageBefore ;

    private String imageAfter ;

    public String getImageBefore() {
        return imageBefore;
    }

    public void setImageBefore(String imageBefore) {
        this.imageBefore = imageBefore;
    }

    public String getImageAfter() {
        return imageAfter;
    }

    public void setImageAfter(String imageAfter) {
        this.imageAfter = imageAfter;
    }

    private String brokerName;

    private String accountCreateDate;

    private Date registeredDate;

    @NotBlank(message = "Trạng thái hợp đồng không thể bỏ trống")
    private String contractStatus;

    private Long hdnSourceId;

    private Long hdnEmployeeId;

    private Date updDate;

    private String updUser;

    private ComboboxForm comboboxForm;

    public Long getHdnCustomerCode() {
        return hdnCustomerCode;
    }

    public void setHdnCustomerCode(Long hdnCustomerCode) {
        this.hdnCustomerCode = hdnCustomerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getAccountCreateDate() {
        return accountCreateDate;
    }

    public void setAccountCreateDate(String accountCreateDate) {
        this.accountCreateDate = accountCreateDate;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

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

    public String getIdentifyNumber() {
        return identifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public ComboboxForm getComboboxForm() {
        return comboboxForm;
    }

    public void setComboboxForm(ComboboxForm comboboxForm) {
        this.comboboxForm = comboboxForm;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getOwnerBankingName() {
        return ownerBankingName;
    }

    public void setOwnerBankingName(String ownerBankingName) {
        this.ownerBankingName = ownerBankingName;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Long getHdnSourceId() {
        return hdnSourceId;
    }

    public void setHdnSourceId(Long hdnSourceId) {
        this.hdnSourceId = hdnSourceId;
    }

    public Long getHdnEmployeeId() {
        return hdnEmployeeId;
    }

    public void setHdnEmployeeId(Long hdnEmployeeId) {
        this.hdnEmployeeId = hdnEmployeeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
