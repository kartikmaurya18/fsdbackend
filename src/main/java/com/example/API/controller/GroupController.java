package com.example.API.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.API.entity.Group;
import com.example.API.Repository.GroupRepository;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

    // Get all groups
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Group>> getAllGroups() {
        logger.info("Fetching all groups");
        List<Group> groupList = groupRepository.findAll();
        return new ResponseEntity<>(groupList, HttpStatus.OK);
    }

    // Get group by ID
    @GetMapping(path = "/{groupId}", produces = "application/json")
    public ResponseEntity<Object> getGroupById(@PathVariable String groupId) { // Change Long to String
        logger.info("Fetching group with ID: " + groupId);
        Optional<Group> group = groupRepository.findById(groupId); // This must match the type
        if (group.isPresent()) {
            return new ResponseEntity<>(group.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
        }
    }

    // Create a new group
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Group> createGroup(@RequestBody Group newGroup) {
        logger.info("Creating new group with topic: " + newGroup.getGroup_topic());
        Group savedGroup = groupRepository.save(newGroup);
        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    // Update a group
    @PutMapping(path = "/{groupId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateGroup(@PathVariable String groupId, @RequestBody Group updatedGroup) {
        logger.info("Updating group with ID: " + groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isPresent()) {
            Group existingGroup = groupOptional.get();
            existingGroup.setGroup_topic(updatedGroup.getGroup_topic());
            Group savedGroup = groupRepository.save(existingGroup);
            return new ResponseEntity<>(savedGroup, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
        }
    }

    // Delete a group
    @DeleteMapping(path = "/{groupId}")
    public ResponseEntity<Object> deleteGroup(@PathVariable String groupId) {
        logger.info("Deleting group with ID: " + groupId);
        if (groupRepository.existsById(groupId)) {
            groupRepository.deleteById(groupId);
            return new ResponseEntity<>("Group deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
        }
    }
}
