package com.example.API.controller;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.API.Repository.BatchRepository;
import com.example.API.Response.ResponseBean;
import com.example.API.entity.Batch;

@RestController
@RequestMapping("/batches")
public class BatchController {

    @Autowired
    private BatchRepository batchRepo;

    @Autowired
    private ResponseBean response;

    private static final Logger logger = LoggerFactory.getLogger(BatchController.class);

    // Get all batches
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getBatches() {
        logger.info("Fetching all batches");
        List<Batch> batchList = batchRepo.findAll();
        response.setData(batchList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a batch by number
    @GetMapping(path = "/{batch_no}", produces = "application/json")
    public ResponseEntity<Object> getBatchByNo(@PathVariable int batch_no) {
        logger.info("Fetching batch with batch number: {}", batch_no);
        Optional<Batch> batch = batchRepo.findById(batch_no);
        if (batch.isPresent()) {
            response.setData(batch.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("Batch not found");
            logger.warn("Batch not found for batch number: {}", batch_no);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Create a new batch
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createBatch(@RequestBody Batch batch) {
        try {
            logger.info("Creating a new batch with batch number: {}", batch.getBatch_no());
            // Set the created and modified date
            LocalDateTime now = LocalDateTime.now();
            batch.setCreated_date(now);
            batch.setModified_date(now);

            Batch savedBatch = batchRepo.save(batch);
            response.setErrorCode(null);
            response.setData(savedBatch);
            logger.info("Batch created successfully with batch number: {}", batch.getBatch_no());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating batch: {}", e.getMessage());
            response.setErrorCode("ERR500");
            response.setData("An error occurred while creating the batch.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing batch
    @PutMapping(path = "/{batch_no}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateBatch(@PathVariable int batch_no, @RequestBody Batch batchDetails) {
        logger.info("Updating batch with batch number: {}", batch_no);
        try {
            Optional<Batch> batch = batchRepo.findById(batch_no);
            if (batch.isPresent()) {
                Batch existingBatch = batch.get();
                existingBatch.setBranch(batchDetails.getBranch());
                existingBatch.setVenue(batchDetails.getVenue());
                existingBatch.setCreated_by(batchDetails.getCreated_by());
                existingBatch.setModified_by(batchDetails.getModified_by());
                existingBatch.setModified_date(LocalDateTime.now()); // Update modified date

                Batch updatedBatch = batchRepo.save(existingBatch);
                response.setErrorCode(null);
                response.setData(updatedBatch);
                logger.info("Batch updated successfully with batch number: {}", batch_no);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("Batch not found");
                logger.warn("Batch not found for batch number: {}", batch_no);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating batch with batch number {}: {}", batch_no, e.getMessage());
            response.setErrorCode("ERR500");
            response.setData("An error occurred while updating the batch.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a batch
    @DeleteMapping(path = "/{batch_no}")
    public ResponseEntity<Object> deleteBatch(@PathVariable int batch_no) {
        logger.info("Deleting batch with batch number: {}", batch_no);
        try {
            if (batchRepo.existsById(batch_no)) {
                batchRepo.deleteById(batch_no);
                response.setErrorCode(null);
                response.setData("Batch with number " + batch_no + " deleted successfully");
                logger.info("Batch with batch number {} deleted successfully", batch_no);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("Batch not found");
                logger.warn("Batch not found for batch number: {}", batch_no);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting batch with batch number {}: {}", batch_no, e.getMessage());
            response.setErrorCode("ERR500");
            response.setData("An error occurred while deleting the batch.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
