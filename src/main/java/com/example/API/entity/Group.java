package com.example.API.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_details") // Make sure this matches your actual table name
public class Group {
    @Id
    private String group_id; // Keep this as String if your IDs are like "G001"
    private String group_topic;

    // Default constructor
    public Group() {}

    // Constructor
    public Group(String group_id, String group_topic) {
        this.group_id = group_id;
        this.group_topic = group_topic;
    }

    // Getters and Setters
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
}
