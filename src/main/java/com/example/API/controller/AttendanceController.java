package com.example.API.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.API.Repository.AttendanceRepository;
import com.example.API.Response.ResponseBean;
import com.example.API.entity.Attendance;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {

    @Autowired
    public AttendanceRepository attendanceRepo;

    @Autowired
    public ResponseBean response;

    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    // Get all attendance records
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getAttendances() {
        logger.info("Fetching all attendances");
        List<Attendance> attendanceList = attendanceRepo.findAll();
        response.setData(attendanceList);
        logger.debug("Attendance data: " + attendanceList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get attendance by UID
    @GetMapping(path = "/{uid}", produces = "application/json")
    public ResponseEntity<Object> getAttendanceByUid(@PathVariable String uid) {
        logger.info("Fetching attendance for UID: " + uid);
        try {
            Optional<Attendance> attendance = attendanceRepo.findById(uid);
            if (attendance.isPresent()) {
                response.setErrorCode(null);  
                response.setData(attendance.get());
                logger.debug("Attendance data for UID " + uid + ": " + attendance.get());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("Attendance record not found");
                response.setData(null);  
                logger.warn("Attendance record not found for UID: " + uid);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching attendance for UID: " + uid + ": " + e.getMessage());
            response.setErrorCode("ERR500");  
            response.setData("An error occurred while retrieving attendance: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a new attendance record
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createAttendance(@RequestBody Attendance attendance) {
        logger.info("Creating new attendance record for UID: " + attendance.getUid());
        try {
            Attendance savedAttendance = attendanceRepo.save(attendance);
            response.setErrorCode(null);  
            response.setData(savedAttendance);
            logger.info("Attendance record created successfully for UID: " + attendance.getUid());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating attendance record: " + e.getMessage());
            response.setErrorCode("ERR500");  
            response.setData("An error occurred while creating attendance: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing attendance record
    @PutMapping(path = "/{uid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateAttendance(@PathVariable String uid, @RequestBody Attendance updatedAttendance) {
        logger.info("Updating attendance record for UID: " + uid);
        try {
            Optional<Attendance> attendanceOpt = attendanceRepo.findById(uid);
            if (attendanceOpt.isPresent()) {
                Attendance existingAttendance = attendanceOpt.get();
                existingAttendance.setTotal_lectures(updatedAttendance.getTotal_lectures());
                existingAttendance.setPresent_lectures(updatedAttendance.getPresent_lectures());
                existingAttendance.setPresent_percent(updatedAttendance.getPresent_percent());
                existingAttendance.setModified_by(updatedAttendance.getModified_by());
                existingAttendance.setModified_date(updatedAttendance.getModified_date());

                Attendance savedAttendance = attendanceRepo.save(existingAttendance);
                response.setErrorCode(null);
                response.setData(savedAttendance);
                logger.info("Attendance record updated successfully for UID: " + uid);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("Attendance record not found");
                response.setData(null);  
                logger.warn("Attendance record not found for UID: " + uid);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating attendance record for UID: " + uid + ": " + e.getMessage());
            response.setErrorCode("ERR500");  
            response.setData("An error occurred while updating attendance: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete an attendance record
    @DeleteMapping(path = "/{uid}")
    public ResponseEntity<Object> deleteAttendance(@PathVariable String uid) {
        logger.info("Deleting attendance for UID: " + uid);
        try {
            if (attendanceRepo.existsById(uid)) {
                attendanceRepo.deleteById(uid);
                response.setErrorCode(null);  
                response.setData("Attendance record with UID " + uid + " deleted successfully");
                logger.info("Deleted attendance for UID: " + uid);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("Attendance record not found");
                response.setData(null);  
                logger.warn("Attendance record not found for UID: " + uid);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting attendance for UID: " + uid + ": " + e.getMessage());
            response.setErrorCode("ERR500");  
            response.setData("An error occurred while deleting attendance: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
