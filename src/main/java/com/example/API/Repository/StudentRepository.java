package com.example.API.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.API.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
   
}
