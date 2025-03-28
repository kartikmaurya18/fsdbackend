package com.example.API.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.API.entity.Mentor;

public interface MentorRepository extends JpaRepository<Mentor, String> {
}
