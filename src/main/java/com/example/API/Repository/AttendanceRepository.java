package com.example.API.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.API.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
}

