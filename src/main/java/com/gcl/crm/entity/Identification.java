package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "identification")
public class Identification {

    @Id
    @Column(name = "identity_number", length = 20)
    private String identityNumber;

    @Column(name = "issue_place", nullable = false)
    private String issuePlace;

    @Column(name = "front_image_url", nullable = false)
    private String frontImageUrl;

    @Column(name = "back_image_url", nullable = false)
    private String backImageUrl;

    @Column(name = "ethnic_group", nullable = false)
    private String ethnicGroup;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "date_of_birth", nullable = false)
    private Date birthDate;

    @Column(name = "permanent_place", nullable = false)
    private String permanentPlace;

    @OneToOne(mappedBy = "identification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Employee employee;
}
