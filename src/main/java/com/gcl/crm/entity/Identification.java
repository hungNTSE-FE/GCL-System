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

    @Column(name = "issue_place")
    private String issuePlace;

    @Column(name = "front_image_url")
    private String frontImageUrl;

    @Column(name = "back_image_url")
    private String backImageUrl;

    @Column(name = "ethnic_group")
    private String ethnicGroup;

    @Column(name = "issue_date")
    private Date issueDate;

    @Column(name = "date_of_birth")
    private Date birthDate;

    @Column(name = "permanent_place")
    private String permanentPlace;

    @OneToOne(mappedBy = "identification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Employee employee;
}
