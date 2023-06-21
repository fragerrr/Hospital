package com.example.project.repository;

import com.example.project.model.Doctor;
import com.example.project.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findByUserId(Integer id);

}
