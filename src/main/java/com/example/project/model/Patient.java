package com.example.project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;



@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Patient  {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @Column(name = "name")
    @NotBlank(message = "Name can't be empty")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "Surname can't be empty")
    private String surname;

    @Column(name = "age")
    @NotNull(message = "Enter the age number")
    private Integer age;
    @Column(name = "weight")
    @NotNull(message = "Enter the weight")
    private Integer weight;

    @Column(name = "height")
    @NotNull(message = "Enter the height")
    private Integer height;
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "list_of_sick",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "sick_id")
    )
    private List<Sick> historyOfSick;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @NotNull(message = "Patient should have doctor")
    private Doctor doctor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<Sick> getHistoryOfSick() {
        return historyOfSick;
    }

    public void setHistoryOfSick(List<Sick> historyOfSick) {
        this.historyOfSick = historyOfSick;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
