package com.example.project.controllers;


import com.example.project.model.*;
import com.example.project.security.UsersDetails;
import com.example.project.service.AdminService;
import com.example.project.service.DoctorService;
import com.example.project.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AdminService adminService;

    @Autowired
    public MainController(DoctorService doctorService, PatientService patientService, AdminService adminService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.adminService = adminService;
    }


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UsersDetails usersDetails = (UsersDetails) authentication.getPrincipal();
        Users user = usersDetails.getUser();

        if(user.getRole().equalsIgnoreCase("ROLE_ADMIN")){
            return "redirect:/admin";
        } else if(user.getRole().equalsIgnoreCase("ROLE_DOCTOR")){
            return "redirect:/doctor";
        } else{
            Patient patient = patientService.findByUserId(user.getId());
            model.addAttribute("patient", patient);
            return "patient/patient";
        }

    }

    @GetMapping("/403")
    public String error403(){
        return "error/403";
    }
}
