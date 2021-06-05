package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "action_type")
public class ActionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "actionType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Action> actions;
}
