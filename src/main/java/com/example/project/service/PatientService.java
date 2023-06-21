package com.example.project.service;


import com.example.project.model.Patient;
import com.example.project.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientService {
    private final PatientRepository patientRepository;


    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient findByUserId(Integer id){
        return patientRepository.findByUserId(id);
    }

    public Patient findById(Integer id){
        return patientRepository.findById(id).orElse(null);
    }

    public List<Patient> findAll(){
        return patientRepository.findAll();
    }
    public void addPatient(Patient patient){
        patientRepository.save(patient);
    }

    public void updatePatient(Patient patient){
        Patient patient1 = patientRepository.findById(patient.getId()).orElse(null);

        patient1.setHistoryOfSick(patient1.getHistoryOfSick());

        patientRepository.save(patient1);
    }

    public void delete(Patient patient) {
        patientRepository.delete(patient);
    }
}
