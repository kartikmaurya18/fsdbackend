package com.example.API.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.API.Repository.StudentRepository;
import com.example.API.Response.ResponseBean;
import com.example.API.entity.Student;

@RestController
public class StudentController {

    @Autowired
    public StudentRepository studentrepo;

    @Autowired
    public ResponseBean response;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @GetMapping(path = "/students", produces = "application/json")
    public ResponseEntity<Object> getStudents() {
        logger.info("Fetching all students");
        List<Student> studentList = studentrepo.findAll();
        response.setData(studentList);
        logger.debug("Student data: {}", studentList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/students/{uid}", produces = "application/json")
    public ResponseEntity<Object> getUid(@PathVariable String uid) {
        logger.info("Fetching student with UID: {}", uid);
        try {
            Optional<Student> student = studentrepo.findById(uid);
            if (student.isPresent()) {
                response.setData(student.get());
                logger.debug("Student data for UID {}: {}", uid, student.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("Student not found");
                logger.warn("Student not found for UID: {}", uid);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching student for UID: {}: {}", uid, e.getMessage());
            response.setErrorCode("ERR500");
            response.setData("An error occurred: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/students", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createStudent(@RequestBody Student newStudent) {
        logger.info("Creating new student with UID: {}", newStudent.getUid());
        try {
            if (studentrepo.existsById(newStudent.getUid())) {
                response.setErrorCode("ERR005");
                response.setData("Student with id " + newStudent.getUid() + " already exists");
                logger.warn("Student with UID {} already exists", newStudent.getUid());
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
            Student savedStudent = studentrepo.save(newStudent);
            response.setErrorCode(null);
            response.setData(savedStudent);
            logger.info("Student created with UID: {}", savedStudent.getUid());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating student: {}", e.getMessage());
            response.setErrorCode("ERR006");
            response.setData("Error creating student: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/students/{uid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateStudent(@PathVariable String uid, @RequestBody Student updatedStudent) {
        logger.info("Updating student with UID: {}", uid);
        try {
            if (!studentrepo.existsById(uid)) {
                response.setErrorCode("ERR002");
                response.setData("Student with id " + uid + " not found");
                logger.warn("Student with UID {} not found", uid);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            updatedStudent.setUid(uid);
            Student savedStudent = studentrepo.save(updatedStudent);
            response.setErrorCode(null);
            response.setData(savedStudent);
            logger.info("Student updated with UID: {}", savedStudent.getUid());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating student with UID: {}: {}", uid, e.getMessage());
            response.setErrorCode("ERR003");
            response.setData("Error updating student: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/students/{uid}")
    public ResponseEntity<Object> deleteStudent(@PathVariable String uid) {
        logger.info("Deleting student with UID: {}", uid);
        try {
            if (studentrepo.existsById(uid)) {
                studentrepo.deleteById(uid);
                response.setErrorCode(null);
                response.setData("Student with id " + uid + " deleted successfully");
                logger.info("Student with UID {} deleted successfully", uid);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("ERR001");
                response.setData("Student with id " + uid + " not found");
                logger.warn("Student with UID {} not found", uid);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting student with UID: {}: {}", uid, e.getMessage());
            response.setErrorCode("ERR004");
            response.setData("Error deleting student: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
