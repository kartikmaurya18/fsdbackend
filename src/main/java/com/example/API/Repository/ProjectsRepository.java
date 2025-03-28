package com.example.API.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.API.entity.Projects;

public interface ProjectsRepository extends JpaRepository<Projects, Integer> {
}

