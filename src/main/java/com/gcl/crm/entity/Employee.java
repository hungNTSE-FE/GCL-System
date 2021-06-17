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

}
