package com.example.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table

@AllArgsConstructor
@NoArgsConstructor
public class Sick{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Name can't be blank")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Description can't be empty")
    private String description;

    @ManyToMany(mappedBy = "historyOfSick", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Patient> patientList;


    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }
}
