package com.example.project.repository;


import com.example.project.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByUserId(Integer id);
}
