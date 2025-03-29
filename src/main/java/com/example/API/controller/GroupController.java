package com.example.API.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.API.entity.Group;
import com.example.API.entity.Batch;
import com.example.API.Repository.GroupRepository;
import com.example.API.Repository.BatchRepository;
import com.example.API.Response.ResponseBean;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private ResponseBean response;

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    // ✅ Get all groups (without students to avoid large payloads)
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getAllGroups() {
        logger.info("Fetching all groups");
        response.setData(groupRepository.findAll());
        return ResponseEntity.ok(response);
    }

    // ✅ Get group by ID
    @GetMapping("/{group_id}/students")
    public ResponseEntity<Object> getStudentsInGroup(@PathVariable String group_id) {
        logger.info("Fetching all students in group with ID: {}", group_id);

        Optional<Group> groupOpt = groupRepository.findById(group_id);
        if (groupOpt.isEmpty()) {
            response.setErrorCode("ERR002");
            response.setData("Group with ID " + group_id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // ✅ Fetch students in the group
        Group group = groupOpt.get();
        response.setData(group.getStudents()); // Ensure `getStudents()` exists in `Group` entity
        return ResponseEntity.ok(response);
    }

    // ✅ Create a new group
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createGroup(@RequestBody Group newGroup) {
        logger.info("Creating new group with topic: {}", newGroup.getGroup_topic());

        if (groupRepository.existsById(newGroup.getGroup_id())) {
            response.setErrorCode("ERR005");
            response.setData("Group with ID " + newGroup.getGroup_id() + " already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // ✅ Validate batch before saving
        if (newGroup.getBatch() != null) {
            Optional<Batch> batch = batchRepository.findById(newGroup.getBatch().getBatch_id());
            if (batch.isEmpty()) {
                response.setErrorCode("ERR006");
                response.setData("Invalid batch ID: " + newGroup.getBatch().getBatch_id());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            newGroup.setBatch(batch.get());
        }

        Group savedGroup = groupRepository.save(newGroup);
        response.setData(savedGroup);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ Update a group
    @PutMapping("/{group_id}")
    public ResponseEntity<Object> updateGroup(@PathVariable String group_id, @RequestBody Group updatedGroup) {
        logger.info("Updating group with ID: {}", group_id);

        Optional<Group> existingGroupOpt = groupRepository.findById(group_id);
        if (existingGroupOpt.isEmpty()) {
            response.setErrorCode("ERR002");
            response.setData("Group with ID " + group_id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Group existingGroup = existingGroupOpt.get();
        existingGroup.setGroup_topic(updatedGroup.getGroup_topic());
        existingGroup.setProject_title(updatedGroup.getProject_title());
        existingGroup.setProject_desc(updatedGroup.getProject_desc());

        // ✅ Ensure batch exists before updating
        if (updatedGroup.getBatch() != null) {
            Optional<Batch> batch = batchRepository.findById(updatedGroup.getBatch().getBatch_id());
            if (batch.isEmpty()) {
                response.setErrorCode("ERR006");
                response.setData("Invalid batch ID: " + updatedGroup.getBatch().getBatch_id());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            existingGroup.setBatch(batch.get());
        }

        Group savedGroup = groupRepository.save(existingGroup);
        response.setData(savedGroup);
        return ResponseEntity.ok(response);
    }

    // ✅ Delete a group (without cascading delete to students)
    @DeleteMapping("/{group_id}")
    public ResponseEntity<Object> deleteGroup(@PathVariable String group_id) {
        logger.info("Deleting group with ID: {}", group_id);

        Optional<Group> groupOpt = groupRepository.findById(group_id);
        if (groupOpt.isEmpty()) {
            response.setErrorCode("ERR001");
            response.setData("Group with ID " + group_id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Group group = groupOpt.get();
        if (group.getStudents() != null && !group.getStudents().isEmpty()) {
            response.setErrorCode("ERR007");
            response.setData("Cannot delete group with associated students. Please remove students first.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        groupRepository.deleteById(group_id);
        response.setData("Group with ID " + group_id + " deleted successfully");
        return ResponseEntity.ok(response);
    }
}
