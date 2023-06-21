package com.example.project.controllers;


import com.example.project.model.Admin;
import com.example.project.model.Doctor;
import com.example.project.model.Patient;
import com.example.project.model.Users;
import com.example.project.security.UsersDetails;
import com.example.project.service.AdminService;
import com.example.project.service.DoctorService;
import com.example.project.service.PatientService;
import com.example.project.service.UsersDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

    private Admin admin;
    private List<Doctor> doctors;

    private final UsersDetailsService usersDetailsService;
    private final DoctorService doctorService;

    private final AdminService adminService;
    private final PatientService patientService;
    @Autowired
    public AdminController(UsersDetailsService usersDetailsService, DoctorService doctorService, AdminService adminService, PatientService patientService) {
        this.usersDetailsService = usersDetailsService;
        this.doctorService = doctorService;
        this.adminService = adminService;
        this.patientService = patientService;


    }


    @GetMapping()
    public String register(@ModelAttribute("doct") Doctor doctor,
                           @ModelAttribute("patient") Patient patient,
                           Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        Users user = usersDetails.getUser();

        admin = adminService.findByUserId(user.getId());
        doctors = doctorService.findAll();

        model.addAttribute("admin", admin);
        model.addAttribute("doctors", doctors);

        return "admin/admin";
    }


    @GetMapping("/doctors")
    public String listOfDoctors(Model model){
        model.addAttribute("admin", admin);
        model.addAttribute("doctors", doctorService.findAll());

        return "admin/doctors";
    }

    @GetMapping("/patients")
    public String listOfPatients(Model model){
        model.addAttribute("admin", admin);
        model.addAttribute("patients", patientService.findAll());

        return "admin/patients";
    }
    @PostMapping("/new-doctor")
    public String new_doctor(Model model,
                             @ModelAttribute("patient") Patient patient,
                             @ModelAttribute("doct") @Valid Doctor doctor,
                             BindingResult bindingResult,
                             @RequestParam(name = "doctor_pass") String pass){
        if(pass == null || pass.equalsIgnoreCase("")){
            pass = "admin";
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("admin", admin);
            model.addAttribute("doctors", doctors);
            return "admin/admin";
        }

        doctor.setUser(usersDetailsService.register(new Users(null, pass, "ROLE_DOCTOR")));

        doctorService.addDoctor(doctor);

        return "redirect:/admin?id=" + doctor.getUser().getId();
    }

    @PostMapping("/new-patient")
    public String new_patient( Model model,
                               @ModelAttribute("doct") Doctor doctor,
                               @ModelAttribute("patient") @Valid Patient patient,
                              BindingResult bindingResult,
                              @RequestParam(name = "patient_pass") String patient_pass){

        if(patient_pass==null || patient_pass.equalsIgnoreCase("")){
            patient_pass="admin";
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("admin", admin);
            model.addAttribute("doctors", doctors);
            return "admin/admin";
        }

        patient.setDoctor(doctorService.getDoctor(patient.getDoctor().getId()));

        patient.setUser(usersDetailsService.register(new Users(null, patient_pass, "ROLE_PATIENT")));

        patientService.addPatient(patient);

        return "redirect:/admin?id=" + patient.getUser().getId();
    }

    @GetMapping("/doctor/{id}/delete")
    public String deleteDoctor(@PathVariable(name = "id") Integer id){

        Doctor doctor = doctorService.getDoctor(id);

        Integer user_id = doctor.getUser().getId();
        doctorService.deleteById(id);
        usersDetailsService.delete(usersDetailsService.loadUserByUsername(user_id.toString()).getUser());

        return "redirect:/admin";
    }

    @GetMapping("/patient/{id}/delete")
    public String deletePatient(@PathVariable(name = "id") Integer id){
        Patient patient = patientService.findById(id);

        Integer user_id = patient.getUser().getId();

        patientService.delete(patient);

        usersDetailsService.delete(usersDetailsService.loadUserByUsername(user_id.toString()).getUser());

        return "redirect:/admin";
    }
}
