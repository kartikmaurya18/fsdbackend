package com.example.API.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.util.List;

@Entity
@Table(name = "batch") // Ensure this matches your database table name
public class Batch {

    @Id
    private String batchId;
    private String branch;
    private String venue;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mentor> mentors;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Group> groups;

    public Batch() {
    }

    public Batch(String batchId, String branch, String venue) {
        this.batchId = batchId;
        this.branch = branch;
        this.venue = venue;
    }
    
    @PrePersist
    @Transactional
    public void generateBatchId() {
        String lastBatchId = findLastBatchId();  // Fetch last batch ID
        int nextNumber = (lastBatchId == null) ? 1 : Integer.parseInt(lastBatchId.substring(1)) + 1;
        this.batchId = String.format("B%02d", nextNumber); // Format as B01, B02, etc.
    }

    private String findLastBatchId() {
        // Fetch last inserted batch ID (This requires a repository, inject it if needed)
        return null;  // Placeholder, implement in service layer
    }
    
    // Getters and setters
    public String getBatch_id() {
        return batchId;
    }

    public void setBatch_id(String batchId) {
        this.batchId = batchId;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Mentor> getMentors() {
        return mentors;
    }

    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
