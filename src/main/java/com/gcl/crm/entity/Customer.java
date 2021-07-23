package com.gcl.crm.entity;

import com.gcl.crm.enums.Gender;
import com.gcl.crm.form.CustomerStatusForm;
import com.gcl.crm.form.CustomerStatusEvaluationForm;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Integer customerId;

    @Column(name = "CUSTOMER_CODE", unique = true)
    private String customerCode;

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

    @Column(name="trading_account")
    private String number;

    @Column(name="contract_number")
    private String contractNumber;

    @Column(name = "ADD_USER")
    private int addUser;

    @Column(name = "UPD_DATE")
    private Date updDate;

    @Column(name = "UPD_USER")
    private int updUser;

    @ManyToOne
    @JoinColumn(name = "LEVEL_ID")
    private Level level;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "identity_number", referencedColumnName = "identity_number")
    private Identification identification;

    @ManyToMany
    @JoinColumn(name = "CAMPAIGN_CODE")
    private List<Campaign> campaignList;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number", referencedColumnName = "account_number")
    private TradingAccount tradingAccount;

    @OneToOne
    @JoinColumn(name = "id")
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
    private Contract contract;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CustomerDistribution> customerDistributionList;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private Source source;

    private String birthDate ;
    private String issueDate ;

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public TradingAccount getTradingAccount() {
        return tradingAccount;
    }

    public void setTradingAccount(TradingAccount tradingAccount) {
        this.tradingAccount = tradingAccount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
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



    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
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

    public List<CustomerDistribution> getCustomerDistributionList() {
        return customerDistributionList;
    }

    public void setCustomerDistributionList(List<CustomerDistribution> customerDistributionList) {
        this.customerDistributionList = customerDistributionList;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
