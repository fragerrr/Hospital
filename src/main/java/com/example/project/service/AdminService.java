package com.example.project.service;

import com.example.project.model.Admin;
import com.example.project.model.Doctor;
import com.example.project.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;


    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin getAdmin(Integer id){
        return adminRepository.findById(id).orElse(null);
    }

    public Admin findByUserId(Integer id){
        return adminRepository.findByUserId(id);
    }

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }
}
