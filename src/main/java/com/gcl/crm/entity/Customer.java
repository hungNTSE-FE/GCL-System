package com.gcl.crm.entity;

import com.gcl.crm.enums.Gender;
import com.gcl.crm.form.CustomerStatusForm;
import com.gcl.crm.form.CustomerStatusEvaluationForm;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "CUSTOMER")
@SqlResultSetMapping(
        name = "getCustomerStatusListMapping",
        classes = @ConstructorResult(
                targetClass = CustomerStatusForm.class
                , columns = {
                @ColumnResult(name = "marketing_name", type = String.class),
                @ColumnResult(name = "level_0", type = String.class),
                @ColumnResult(name = "level_1", type = String.class),
                @ColumnResult(name = "level_2", type = String.class),
                @ColumnResult(name = "level_3", type = String.class),
                @ColumnResult(name = "level_4", type = String.class),
                @ColumnResult(name = "level_5", type = String.class),
                @ColumnResult(name = "level_6", type = String.class),
                @ColumnResult(name = "level_7", type = String.class),
                @ColumnResult(name = "total", type = String.class),
        }
        )
)
@SqlResultSetMapping(
        name = "getCustomerStatusReportListMapping",
        classes = @ConstructorResult(
                targetClass = CustomerStatusEvaluationForm.class
                , columns = {
                @ColumnResult(name = "marketing_name", type = String.class),
                @ColumnResult(name = "level_6", type = Integer.class),
                @ColumnResult(name = "level_7", type = Integer.class),
                @ColumnResult(name = "LOT", type = Integer.class),
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


    public Customer(Integer customerId, String customerCode, String customerName, String phoneNumber, TradingAccount tradingAccount) {
        this.customerId = customerId;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.tradingAccount = tradingAccount;
    }

    public Customer(Integer customerId, String customerName, String phoneNumber, String email, String description) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
    }
    public Customer(Integer customerId, String customerName, String phoneNumber, String email, String description, String number) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.description = description;
        this.number = number;
    }

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
    private Long addUser;

    @Column(name = "UPD_DATE")
    private Date updDate;

    @Column(name = "UPD_USER")
    private Long updUser;

    @ManyToOne
    @JoinColumn(name = "LEVEL_ID")
    private Level level;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "identity_number", referencedColumnName = "identity_number")
    private Identification identification;

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

    @OneToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private CustomerDistribution customerDistribution;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "SOURCE_ID")
    private Source source;

    private String birthDate ;
    private String issueDate ;

    public Customer() {
    }

    public Customer(Integer customerId, String customerName, String phoneNumber, String email, String number, String contractNumber, Source source) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.number = number;
        this.contractNumber = contractNumber;
        this.source = source;
    }

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Long getAddUser() {
        return addUser;
    }

    public void setAddUser(Long addUser) {
        this.addUser = addUser;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public Long getUpdUser() {
        return updUser;
    }

    public void setUpdUser(Long updUser) {
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

    public CustomerDistribution getCustomerDistribution() {
        return customerDistribution;
    }

    public void setCustomerDistribution(CustomerDistribution customerDistribution) {
        this.customerDistribution = customerDistribution;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }
}
