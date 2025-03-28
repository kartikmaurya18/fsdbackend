package com.example.API.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.API.entity.Batch;

public interface BatchRepository extends JpaRepository<Batch, Integer> {
}

