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
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ResponseBean response;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    // ✅ Get All Students
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getStudents() {
        logger.info("Fetching all students");
        List<Student> studentList = studentRepo.findAll();
        response.setData(studentList);
        logger.debug("Student data: {}", studentList);
        return ResponseEntity.ok(response);
    }

    // ✅ Get Student by UID
    @GetMapping("/{uid}")
    public ResponseEntity<Object> getStudentById(@PathVariable String uid) {
        logger.info("Fetching student with UID: {}", uid);
        Optional<Student> student = studentRepo.findById(uid);
        if (student.isPresent()) {
            response.setData(student.get());
            return ResponseEntity.ok(response);
        } else {
            response.setErrorCode("ERR002");
            response.setData("Student with UID " + uid + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // ✅ Create Student
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createStudent(@RequestBody Student newStudent) {
        logger.info("Creating new student: {}", newStudent.getUid());

        if (studentRepo.existsById(newStudent.getUid())) {
            response.setErrorCode("ERR005");
            response.setData("Student with UID " + newStudent.getUid() + " already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Student savedStudent = studentRepo.save(newStudent);
        response.setData(savedStudent);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ Update Student
    @PutMapping("/{uid}")
    public ResponseEntity<Object> updateStudent(@PathVariable String uid, @RequestBody Student updatedStudent) {
        logger.info("Updating student with UID: {}", uid);

        if (!studentRepo.existsById(uid)) {
            response.setErrorCode("ERR002");
            response.setData("Student with UID " + uid + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        updatedStudent.setUid(uid);
        Student savedStudent = studentRepo.save(updatedStudent);
        response.setData(savedStudent);
        return ResponseEntity.ok(response);
    }

    // ✅ Delete Student
    @DeleteMapping("/{uid}")
    public ResponseEntity<Object> deleteStudent(@PathVariable String uid) {
        logger.info("Deleting student with UID: {}", uid);

        if (!studentRepo.existsById(uid)) {
            response.setErrorCode("ERR001");
            response.setData("Student with UID " + uid + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        studentRepo.deleteById(uid);
        response.setData("Student with UID " + uid + " deleted successfully");
        return ResponseEntity.ok(response);
    }
}
