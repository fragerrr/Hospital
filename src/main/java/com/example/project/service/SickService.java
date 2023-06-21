package com.example.project.service;

import com.example.project.model.Sick;
import com.example.project.repository.SickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SickService{
    private final SickRepository sickRepository;

    @Autowired
    public SickService(SickRepository sickRepository) {
        this.sickRepository = sickRepository;
    }

    public Sick save(Sick sick){
        return sickRepository.save(sick);
    }
}
