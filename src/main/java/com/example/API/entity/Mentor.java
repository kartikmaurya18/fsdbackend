package com.example.API.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;

@Entity
public class Mentor {

    @Id
    private String mentor_id;
    private String name;
    private String contact;
    private String email;
    private String gender;
    private Integer batch;
    private String specialization;
    private String department;
  
    public Mentor() {
    }

    public Mentor(String name, String contact, String email, String gender, Integer batch, String specialization, String department) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.gender = gender;
        this.batch = batch;
        this.specialization = specialization;
        this.department = department;
    }
    
    @PrePersist
    @Transactional
    public void generateMentorId() {
        String lastMentorId = findLastMentorId();  // Fetch last mentor ID
        int nextNumber = (lastMentorId == null) ? 1 : Integer.parseInt(lastMentorId.substring(3)) + 1;
        this.mentor_id = String.format("MEN%03d", nextNumber); // Format as MEN001, MEN002, etc.
    }

    private String findLastMentorId() {
        // Fetch last inserted mentor ID (This requires a repository, inject it if needed)
        return null;  // Placeholder, implement in service layer
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getBatch() {
        return batch;
    }
    
    public void setBatch(Integer batch) {
        this.batch = batch;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
}
