package com.example.API.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Attendance {

    @Id
    private String uid;
    private int total_lectures;
    private int present_lectures;
    private int present_percent;
    private String created_by;
    private String created_date;
    private String modified_by;
    private String modified_date;

    public Attendance() {
    }

    public Attendance(String uid, int total_lectures, int present_lectures, int present_percent, String created_by, String created_date, String modified_by, String modified_date) {
        this.uid = uid;
        this.total_lectures = total_lectures;
        this.present_lectures = present_lectures;
        this.present_percent = present_percent;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
    }

    // Getters and setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTotal_lectures() {
        return total_lectures;
    }

    public void setTotal_lectures(int total_lectures) {
        this.total_lectures = total_lectures;
    }

    public int getPresent_lectures() {
        return present_lectures;
    }

    public void setPresent_lectures(int present_lectures) {
        this.present_lectures = present_lectures;
    }

    public int getPresent_percent() {
        return present_percent;
    }

    public void setPresent_percent(int present_percent) {
        this.present_percent = present_percent;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }
}
