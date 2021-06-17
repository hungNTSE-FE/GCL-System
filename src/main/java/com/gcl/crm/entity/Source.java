package com.gcl.crm.entity;

import javax.persistence.*;

@Entity
@Table(name = "SOURCE")
public class Source {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "SOURCE_ID")
    private Long sourceId;

    @Column(name = "SOURCE_NAME")
    private String sourceName;

    @Column(name = "METHODS")
    private String methods;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }
}
