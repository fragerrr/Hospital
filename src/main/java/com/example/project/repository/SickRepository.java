package com.example.project.repository;

import com.example.project.model.Sick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SickRepository extends JpaRepository<Sick, Integer> {
}
