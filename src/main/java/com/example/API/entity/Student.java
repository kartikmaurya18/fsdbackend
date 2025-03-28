package com.example.API.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    private String uid;
    private String classname;
    private String name;
    private String Email;
    private String Contact;
    private Integer Batch_No;
    private Integer groupNo;
  
    public Student() {
    }

   
    public Student(String uid, String classname, String name, String email, String contact, Integer batch_No, Integer groupNo) {
        this.uid = uid;
        this.classname = classname;
        this.name = name;
        this.Email = email;
        this.Contact = contact;
        this.Batch_No = batch_No;
        this.groupNo = groupNo;

    }

    // Getters and setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        this.Contact = contact;
    }

    public Integer getBatch_No() {
        return Batch_No;
    }

    public void setBatch_No(Integer batch_No) {
        this.Batch_No = batch_No;
    }
    
    public Integer getGroupNo() {
        return groupNo;
    }
    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }
}
