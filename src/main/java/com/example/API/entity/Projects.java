package com.example.API.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Projects {

    @Id
    private int batch_no;
    private String domain;
    private String mentor;
    private String examiner;
    private String created_by;

    public Projects() {
    }

    public Projects(int batch_no, String domain, String mentor, String examiner, String created_by) {
        this.batch_no = batch_no;
        this.domain = domain;
        this.mentor = mentor;
        this.examiner = examiner;
        this.created_by = created_by;
    }

    // Getters and setters
    public int getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(int batch_no) {
        this.batch_no = batch_no;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}
