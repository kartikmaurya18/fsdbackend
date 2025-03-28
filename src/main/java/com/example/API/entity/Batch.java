package com.example.API.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "batch") // Ensure this matches your database table name
public class Batch {

    @Id
    private int batch_no;
    private String branch;
    private String venue;
    private String created_by;

    // Use LocalDateTime for date fields
    private LocalDateTime created_date;
    private String modified_by;
    private LocalDateTime modified_date;

    public Batch() {
    }

    public Batch(int batch_no, String branch, String venue, String created_by, LocalDateTime created_date, String modified_by, LocalDateTime modified_date) {
        this.batch_no = batch_no;
        this.branch = branch;
        this.venue = venue;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
    }

    // Getters and setters
    public int getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(int batch_no) {
        this.batch_no = batch_no;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public LocalDateTime getModified_date() {
        return modified_date;
    }

    public void setModified_date(LocalDateTime modified_date) {
        this.modified_date = modified_date;
    }
}
