package com.gcl.crm.entity;

import com.gcl.crm.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "note", length = 1000)
    private String note;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "department_permission", joinColumns = @JoinColumn(name = "department_id"),
                inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    private List<Documentary> documentaries;


}
