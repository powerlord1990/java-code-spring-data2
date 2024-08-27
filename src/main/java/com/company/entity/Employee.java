package com.company.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String position;

    private double salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee(String firstName, String lastName, String position, double salary, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.department = department;
    }
}