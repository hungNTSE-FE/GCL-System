package com.gcl.crm.entity;

import com.gcl.crm.enums.EmployeeStatus;
import com.gcl.crm.enums.Gender;
import com.gcl.crm.form.CustomerStatusEvaluationForm;
import com.gcl.crm.form.EmployeeSearchForm;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Table(name = "employee")
@SqlResultSetMapping(
        name = "getEmployeesByDepartmentId",
        classes = @ConstructorResult(
                targetClass = EmployeeSearchForm.class
                , columns = {
                @ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "name", type = String.class),
        }
        )
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "identity_number", referencedColumnName = "identity_number")
    private Identification identification;

    @Column(name = "full_name")
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "company_email", unique = true)
    private String companyEmail;

    @Column(name = "date_of_birth")
    private Date birthDate;

    @Column(name = "major")
    private String major;

    private String avatar;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "broker_code")
    private Long brokerCode;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private EmployeeStatus status;

    @Column(name = "note", length = 1000)
    private String note;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employee")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MarketingGroup> marketingGroups;

    @ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> tasks;

    public Employee() {
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Employee(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(Long brokerCode) {
        this.brokerCode = brokerCode;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<MarketingGroup> getMarketingGroups() {
        return marketingGroups;
    }

    public void setMarketingGroups(List<MarketingGroup> marketingGroups) {
        this.marketingGroups = marketingGroups;
    }
}
