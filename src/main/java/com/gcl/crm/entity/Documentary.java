package com.gcl.crm.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name ="documentary")
public class Documentary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private  int id;
    @Column(name="name",length = 512 , nullable = false,unique = true)
    private String name ;
    @Column(name="size")
    private long size ;
    @Column(name="uploadTime")
    private Date uploadTime ;
    @Column(name = "content")
    private byte content[];

    public Documentary() {
    }

    public Documentary(int id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Documentary(int id, String name, long size, Date uploadTime) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.uploadTime = uploadTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="documentary_department",joinColumns = @JoinColumn(name="documentary_id"),inverseJoinColumns = @JoinColumn(name="department_id"))
    private List<Department> departments;

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
