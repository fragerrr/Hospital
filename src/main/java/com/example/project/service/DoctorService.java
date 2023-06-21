package com.example.project.service;

import com.example.project.model.Doctor;
import com.example.project.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoctorService{

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor getDoctor(Integer id){
       return doctorRepository.findById(id).orElse(null);
    }

    public Doctor findByUserId(Integer id){
        return doctorRepository.findByUserId(id);
    }

    public void addDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public void deleteById(Integer id) {
        doctorRepository.deleteById(id);
    }
}
