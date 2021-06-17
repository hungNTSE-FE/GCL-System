package com.gcl.crm.entity;

import com.gcl.crm.enums.Gender;
import com.gcl.crm.form.CustomerStatusForm;
import com.gcl.crm.form.CustomerStatusEvaluationForm;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@SqlResultSetMapping(
        name = "getCustomerStatusListMapping",
        classes = @ConstructorResult(
                targetClass = CustomerStatusForm.class
                , columns = {
                @ColumnResult(name = "employee_name", type = String.class),
                @ColumnResult(name = "level_0", type = String.class),
                @ColumnResult(name = "level_1", type = String.class),
                @ColumnResult(name = "level_2", type = String.class),
                @ColumnResult(name = "level_3", type = String.class),
                @ColumnResult(name = "level_4", type = String.class),
                @ColumnResult(name = "level_5", type = String.class),
                @ColumnResult(name = "level_6", type = String.class),
                @ColumnResult(name = "level_7", type = String.class),
        }
        )
)
@SqlResultSetMapping(
        name = "getCustomerStatusReportListMapping",
        classes = @ConstructorResult(
                targetClass = CustomerStatusEvaluationForm.class
                , columns = {
                @ColumnResult(name = "employee_name", type = String.class),
                @ColumnResult(name = "level_6", type = Integer.class),
                @ColumnResult(name = "level_7", type = Integer.class)
            }
        )
)
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMER_CODE")
    private Long customerCode;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "GENDER")
    private Gender gender;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACCOUNT_REGISTER_DATE")
    private Date accountRegisterDate;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "ADD_USER")
    private String addUser;

    @Column(name = "UPD_DATE")
    private Date updDate;

    @Column(name = "UPD_USER")
    private Date updUser;

    @ManyToOne
    @JoinColumn(name = "LEVEL_ID")
    private Level level;

    @OneToOne
    @JoinColumn(name = "identity_number")
    private Identification identification;

    @ManyToMany
    @JoinColumn(name = "CAMPAIGN_CODE")
    private List<Campaign> campaignList;

    @OneToOne
    @JoinColumn(name = "id")
    private Employee employee;

    public Long getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(Long customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

    public Date getAccountRegisterDate() {
        return accountRegisterDate;
    }

    public void setAccountRegisterDate(Date registerDate) {
        this.accountRegisterDate = registerDate;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public List<Campaign> getCampaignList() {
        return campaignList;
    }

    public void setCampaignList(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public Date getUpdUser() {
        return updUser;
    }

    public void setUpdUser(Date updUser) {
        this.updUser = updUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
