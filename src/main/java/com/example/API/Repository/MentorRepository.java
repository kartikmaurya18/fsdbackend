package com.example.API.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.API.entity.Mentor;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, String> {

    @Query("SELECT m.mentor_id FROM Mentor m ORDER BY m.mentor_id DESC LIMIT 1")
    String findLastMentorId();
}
