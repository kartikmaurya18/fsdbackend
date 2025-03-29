package com.example.API.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.API.entity.Batch;

public interface BatchRepository extends JpaRepository<Batch, String> {
    List<Batch> findByBatchId(String batchId); // ✅ Use "batchId" instead of "batchID"
}

