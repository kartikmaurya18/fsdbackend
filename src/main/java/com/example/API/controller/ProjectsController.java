package com.example.API.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.API.Repository.ProjectsRepository;
import com.example.API.Response.ResponseBean;
import com.example.API.entity.Projects;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

    @Autowired
    private ProjectsRepository projectsRepo;

    @Autowired
    private ResponseBean response;

    private static final Logger logger = LoggerFactory.getLogger(ProjectsController.class);

    // Get all projects
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getProjects() {
        logger.info("Fetching all projects");
        List<Projects> projectList = projectsRepo.findAll();
        response.setData(projectList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get project by batch number
    @GetMapping(path = "/{batch_no}", produces = "application/json")
    public ResponseEntity<Object> getProjectByBatchNo(@PathVariable int batch_no) {
        logger.info("Fetching project with Batch No: {}", batch_no);
        Optional<Projects> project = projectsRepo.findById(batch_no);
        if (project.isPresent()) {
            response.setData(project.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setErrorCode("Project not found");
            logger.warn("Project not found for Batch No: {}", batch_no);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Create a new project
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createProject(@RequestBody Projects projectDetails) {
        logger.info("Creating a new project with Batch No: {}", projectDetails.getBatch_no());
        try {
            Projects savedProject = projectsRepo.save(projectDetails);
            response.setData(savedProject);
            logger.info("Project created successfully with Batch No: {}", savedProject.getBatch_no());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating project: {}", e.getMessage());
            response.setErrorCode("ERR500");
            response.setData("An error occurred while creating the project.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    // Update an existing project
    @PutMapping(path = "/{batch_no}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateProject(@PathVariable int batch_no, @RequestBody Projects projectDetails) {
        logger.info("Updating project with Batch No: {}", batch_no);
        try {
            Optional<Projects> project = projectsRepo.findById(batch_no);
            if (project.isPresent()) {
                Projects existingProject = project.get();
                existingProject.setDomain(projectDetails.getDomain());
                existingProject.setMentor(projectDetails.getMentor());
                existingProject.setExaminer(projectDetails.getExaminer());
                existingProject.setCreated_by(projectDetails.getCreated_by());

                Projects updatedProject = projectsRepo.save(existingProject);
                response.setErrorCode(null);
                response.setData(updatedProject);
                logger.info("Project updated successfully with Batch No: {}", batch_no);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("Project not found");
                logger.warn("Project not found for Batch No: {}", batch_no);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating project with Batch No {}: {}", batch_no, e.getMessage());
            response.setErrorCode("ERR500");
            response.setData("An error occurred while updating the project.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a project
    @DeleteMapping(path = "/{batch_no}")
    public ResponseEntity<Object> deleteProject(@PathVariable int batch_no) {
        logger.info("Deleting project with Batch No: {}", batch_no);
        try {
            if (projectsRepo.existsById(batch_no)) {
                projectsRepo.deleteById(batch_no);
                response.setErrorCode(null);
                response.setData("Project with Batch No " + batch_no + " deleted successfully");
                logger.info("Project with Batch No {} deleted successfully", batch_no);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setErrorCode("Project not found");
                logger.warn("Project not found for Batch No: {}", batch_no);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error deleting project with Batch No {}: {}", batch_no, e.getMessage());
            response.setErrorCode("ERR500");
            response.setData("An error occurred while deleting the project.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
