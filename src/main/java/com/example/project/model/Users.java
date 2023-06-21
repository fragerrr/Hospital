package com.example.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Doctor doctor;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Patient patient;

    public Users(Integer id, String password, String role){
        this.id = id;
        this.password = password;
        this.role = role;
    }
    @PreRemove
    private void removeDoctor(){
        if(this.patient != null){
            this.patient.setUser(null);
        }
        if(this.doctor != null){
            this.doctor.setUser(null);
        }


    }

}
