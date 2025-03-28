package com.example.API.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.API.entity.Group;

public interface GroupRepository extends JpaRepository<Group, String> { // This should be String
}
