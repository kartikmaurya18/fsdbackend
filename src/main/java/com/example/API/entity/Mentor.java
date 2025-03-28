package com.example.API.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Mentor {

    @Id
    private String mentor_id;
    private String name;
    private String contact;
    private String email;
    private String department;
    private String created_by;

    public Mentor() {
    }

    public Mentor(String mentor_id, String name, String contact, String email, String department, String created_by) {
        this.mentor_id = mentor_id;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.department = department;
        this.created_by = created_by;
    }

    // Getters and setters
    public String getMentor_id() {
        return mentor_id;
    }

    public void setMentor_id(String mentor_id) {
        this.mentor_id = mentor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}
