package com.gcl.crm.entity;

import com.gcl.crm.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "action")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id")
    private Long id;

    @Column(name = "action_name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToMany(mappedBy = "actions", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Permission> permissions;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private ActionType actionType;
}
