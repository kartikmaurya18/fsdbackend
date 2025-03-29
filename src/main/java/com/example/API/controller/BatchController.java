package com.example.API.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.API.entity.Batch;
import com.example.API.entity.Student;  // ✅ Import Student entity
import com.example.API.Repository.BatchRepository;
import com.example.API.Repository.StudentRepository;  // ✅ Import StudentRepository
import com.example.API.Response.ResponseBean;

import java.util.List;

@RestController
@RequestMapping("/batches")
public class BatchController {

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private StudentRepository studentRepository;  // ✅ Inject StudentRepository

    @Autowired
    private ResponseBean response;

    private static final Logger logger = LoggerFactory.getLogger(BatchController.class);

    // ✅ Get all batches
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getAllBatches() {
        logger.info("Fetching all batches");
        List<Batch> batchList = batchRepository.findAll();
        response.setData(batchList);
        return ResponseEntity.ok(response);
    }

    // ✅ Get batch by ID
    @GetMapping("/{batch_id}")
    public ResponseEntity<Object> getBatchById(@PathVariable String batch_id) {
        logger.info("Fetching batch with ID: {}", batch_id);
        Optional<Batch> batch = batchRepository.findById(batch_id);
        if (batch.isPresent()) {
            response.setData(batch.get());
            return ResponseEntity.ok(response);
        } else {
            response.setErrorCode("ERR002");
            response.setData("Batch with ID " + batch_id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // ✅ Get all students in a batch
    @GetMapping("/{batch_id}/students")  // ✅ New endpoint
    public ResponseEntity<Object> getStudentsByBatch(@PathVariable String batch_id) {
        logger.info("Fetching all students in batch with ID: {}", batch_id);

        // ✅ Check if batch exists
        Optional<Batch> batch = batchRepository.findById(batch_id);
        if (batch.isEmpty()) {
            response.setErrorCode("ERR002");
            response.setData("Batch with ID " + batch_id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // ✅ Fetch students belonging to the batch
        List<Student> students = studentRepository.findByBatch_BatchId(batch_id);  // ✅ Using custom query method
        response.setData(students);
        return ResponseEntity.ok(response);
    }

    // ✅ Create a new batch
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createBatch(@RequestBody Batch newBatch) {
        logger.info("Creating a new batch for branch: {}", newBatch.getBranch());

        if (batchRepository.existsById(newBatch.getBatch_id())) {
            response.setErrorCode("ERR005");
            response.setData("Batch with ID " + newBatch.getBatch_id() + " already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Batch savedBatch = batchRepository.save(newBatch);
        response.setData(savedBatch);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ Update an existing batch
    @PutMapping("/{batch_id}")
    public ResponseEntity<Object> updateBatch(@PathVariable String batch_id, @RequestBody Batch updatedBatch) {
        logger.info("Updating batch with ID: {}", batch_id);

        if (!batchRepository.existsById(batch_id)) {
            response.setErrorCode("ERR002");
            response.setData("Batch with ID " + batch_id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        updatedBatch.setBatch_id(batch_id);
        Batch savedBatch = batchRepository.save(updatedBatch);
        response.setData(savedBatch);
        return ResponseEntity.ok(response);
    }

    // ✅ Delete a batch
    @DeleteMapping("/{batch_id}")
    public ResponseEntity<Object> deleteBatch(@PathVariable String batch_id) {
        logger.info("Deleting batch with ID: {}", batch_id);

        if (!batchRepository.existsById(batch_id)) {
            response.setErrorCode("ERR001");
            response.setData("Batch with ID " + batch_id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        batchRepository.deleteById(batch_id);
        response.setData("Batch with ID " + batch_id + " deleted successfully");
        return ResponseEntity.ok(response);
    }
}
