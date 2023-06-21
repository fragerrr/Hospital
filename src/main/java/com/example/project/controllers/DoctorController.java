package com.example.project.controllers;

import com.example.project.model.Doctor;
import com.example.project.model.Patient;
import com.example.project.model.Sick;
import com.example.project.model.Users;
import com.example.project.security.UsersDetails;
import com.example.project.service.DoctorService;
import com.example.project.service.PatientService;
import com.example.project.service.SickService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctor")
@PreAuthorize("hasAnyRole('DOCTOR')")
public class DoctorController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final SickService sickService;

    private Doctor doctor;

    @Autowired
    public DoctorController(DoctorService doctorService, PatientService patientService, SickService sickService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.sickService = sickService;
    }

    @GetMapping("")
    public String doctor(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        Users user = usersDetails.getUser();

        doctor = doctorService.findByUserId(user.getId());

        model.addAttribute("doctor", doctor);

        return "doctor/doctor";
    }

    @GetMapping("/list")
    public String list(Model model){

        model.addAttribute("doctor", doctor);

        return "doctor/list";
    }

    @GetMapping("/{id}/patient")
    public String patient(@PathVariable(name = "id") Integer id, Model model,
                          @ModelAttribute(name = "sick") Sick sick){
        Patient patient = patientService.findById(id);

        if(patient == null){
            return "error";
        } else {
            model.addAttribute("patient", patient);
        }
        model.addAttribute("doctor", doctor);
        return "doctor/patient";
    }

    @PostMapping("/{id}/add-sick")
    public String addSick(Model model,
                          @PathVariable(name = "id") Integer id,
                          @ModelAttribute(name = "sick") @Valid Sick sick,
                          BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            Patient patient = patientService.findById(id);
            model.addAttribute("patient", patient);
            return "doctor/patient";
        }

        Patient patient = patientService.findById(id);


        if(patient == null){
            return "error";
        } else{
            patient.getHistoryOfSick().add(sickService.save(sick));
        }

        patientService.updatePatient(patient);

        return "redirect:/home";
    }


}
