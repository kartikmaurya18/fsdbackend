package com.example.API.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.API.Repository.MentorRepository;
import com.example.API.Response.ResponseBean;
import com.example.API.entity.Mentor;

@RestController
@RequestMapping("/mentors")
public class MentorController {

    @Autowired
    private MentorRepository mentorRepo;

    @Autowired
    private ResponseBean response;

    private static final Logger logger = LoggerFactory.getLogger(MentorController.class);

    // Get all mentors
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getMentors() {
        logger.info("Fetching all mentors");
        List<Mentor> mentorList = mentorRepo.findAll();
        response.setData(mentorList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get mentor by ID
    @GetMapping(path = "/{mentor_id}", produces = "application/json")
    public ResponseEntity<Object> getMentorById(@PathVariable String mentor_id) {
        logger.info("Fetching mentor with ID: {}", mentor_id);
        Optional<Mentor> mentor = mentorRepo.findById(mentor_id);
        if (mentor.isPresent()) {
            response.setData(mentor.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("Mentor not found");
            logger.warn("Mentor not found for ID: {}", mentor_id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Create a new mentor
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createMentor(@RequestBody Mentor mentor) {
        try {
            logger.info("Creating a new mentor with ID: {}", mentor.getMentor_id());
            Mentor savedMentor = mentorRepo.save(mentor);
            response.setErrorCode(null);
            response.setData(savedMentor);
            logger.info("Mentor created successfully with ID: {}", mentor.getMentor_id());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating mentor: {}", e.getMessage());
            response.setErrorCode("ERR500");
            response.setData("An error occurred while creating the mentor.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing mentor
    @PutMapping(path = "/{mentor_id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateMentor(@PathVariable String mentor_id, @RequestBody Mentor mentorDetails) {
        logger.info("Updating mentor with ID: {}", mentor_id);
        try {
            Optional<Mentor> mentor = mentorRepo.findById(mentor_id);
            if (mentor.isPresent()) {
                Mentor existingMentor = mentor.get();
                existingMentor.setName(mentorDetails.getName());
                existingMentor.setContact(mentorDetails.getContact());
                existingMentor.setEmail(mentorDetails.getEmail());
                existingMentor.setDepartment(mentorDetails.getDepartment());
                existingMentor.setCreated_by(mentorDetails.getCreated_by());

                Mentor updatedMentor = mentorRepo.save(existingMentor);
                response.setErrorCode(null);
                response.setData(updatedMentor);
                logger.info("Mentor updated successfully with ID: {}", mentor_id);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("Mentor not found");
                logger.warn("Mentor not found for ID: {}", mentor_id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating mentor with ID {}: {}", mentor_id, e.getMessage());
            response.setErrorCode("ERR500");
            response.setData("An error occurred while updating the mentor.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a mentor
    @DeleteMapping(path = "/{mentor_id}")
    public ResponseEntity<Object> deleteMentor(@PathVariable String mentor_id) {
        logger.info("Deleting mentor with ID: {}", mentor_id);
        try {
            if (mentorRepo.existsById(mentor_id)) {
                mentorRepo.deleteById(mentor_id);
                response.setErrorCode(null);
                response.setData("Mentor with ID " + mentor_id + " deleted successfully");
                logger.info("Mentor with ID {} deleted successfully", mentor_id);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("Mentor not found");
                logger.warn("Mentor not found for ID: {}", mentor_id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting mentor with ID {}: {}", mentor_id, e.getMessage());
            response.setErrorCode("ERR500");
            response.setData("An error occurred while deleting the mentor.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
