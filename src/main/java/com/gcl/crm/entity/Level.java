package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "LEVEL")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LEVEL_ID")
    private Integer levelId;

    @Column(name = "LEVEL_NAME")
    private String levelName;

    @Column(name = "NOTE")
    private String note;

    public Level() {
    }

    public Level(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
