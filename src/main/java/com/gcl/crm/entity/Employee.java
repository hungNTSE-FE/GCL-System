package com.gcl.crm.entity;

import com.gcl.crm.enums.EmployeeStatus;
import com.gcl.crm.enums.Gender;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Table(name = "employee")
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

    @Column(name = "academic_level")
    private String academicLevel;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "company_email", unique = true)
    private String companyEmail;

    @Column(name = "date_of_birth")
    private Date birthDate;

    @Column(name = "major")
    private String major;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "broker_code", unique = true)
    private Long brokerCode;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private EmployeeStatus status;

    @Column(name = "note", length = 1000)
    private String note;

    @Column(name = "tax_code")
    private String taxCode;

    @OneToOne(mappedBy = "employee")
    private AppUser appUser;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_permission", joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    public Employee() {
    }

    public Employee(Long id) {
        this.id = id;
    }

    //    @OneToOne(mappedBy = "modifier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Department manageDepartment;

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

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
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

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
