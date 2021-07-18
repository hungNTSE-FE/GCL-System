package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Care {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Date lastModified;

    private Long lastModifier;

    private String modifierName;

    private Date acceptDate;

    private boolean accepted;

    private Long acceptor;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "potential_id")
    private Potential potential;
}