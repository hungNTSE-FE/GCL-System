package com.gcl.crm.entity;

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
    @JoinColumn(name = "identity_number", referencedColumnName = "identity_number", nullable = false)
    private Identification identification;

    @Column(name = "full_name", nullable = false)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "phone_number", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "academic_level", nullable = false)
    private String academicLevel;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "company_email", nullable = false, unique = true)
    private String companyEmail;

    @Column(name = "major")
    private String major;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "broker_code", unique = true)
    private Long brokerCode;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "note", length = 1000)
    private String note;

    @Column(name = "tax_code", nullable = false)
    private String taxCode;

    @OneToOne(mappedBy = "employee")
    private AppUser appUser ;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "employee_permission", joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;
}
