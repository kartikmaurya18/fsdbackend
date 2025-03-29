package com.example.API.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "group_details") // ✅ Avoids conflict with reserved keyword
public class Group {  

    @Id
    private String group_id;
    private String group_topic;
    private String project_title;
    private String project_desc;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;

    public Group() { }

    public Group(String group_id, String group_topic, String project_title, String project_desc, Batch batch) {
        this.group_id = group_id;
        this.group_topic = group_topic;
        this.project_title = project_title;
        this.project_desc = project_desc;
        this.batch = batch;
    }

    // ✅ Fixed Getters and Setters
    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_topic() {
        return group_topic;
    }

    public void setGroup_topic(String group_topic) {
        this.group_topic = group_topic;
    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getProject_desc() {
        return project_desc;
    }

    public void setProject_desc(String project_desc) {
        this.project_desc = project_desc;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public List<Student> getStudents() {  // ✅ Fixed
        return students;
    }

    public void setStudents(List<Student> students) {  // ✅ Fixed
        this.students = students;
    }
}
