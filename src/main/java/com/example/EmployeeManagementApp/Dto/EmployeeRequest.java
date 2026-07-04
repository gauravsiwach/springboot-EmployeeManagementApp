package com.example.EmployeeManagementApp.Dto;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;


public class EmployeeRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;
    @Email(message = "Email should be valid")
    private String email;
    @Positive(message = "Salary must be positive")
    private Double salary;
    @NotBlank(message = "Designation is mandatory")
    private String designation;

    private AddressRequest address;

    public EmployeeRequest() {
    }

    public EmployeeRequest(String name, String email, Double salary, String designation, AddressRequest address) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.designation = designation;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }
}