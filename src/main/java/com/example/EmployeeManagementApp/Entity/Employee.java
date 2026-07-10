package com.example.EmployeeManagementApp.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="employees")
public class Employee extends BaseAuditableEntity
{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;
    private String name;
    private String email;
    private Double salary;
    private String designation;

   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "address_id")
   private Address address;

    public Employee() {
    }

    public Employee(String name, String email, Double salary, String designation, Address address) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.designation = designation;
        this.address = address;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
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

   public Address getAddress() {
       return address;
   }

   public void setAddress(Address address) {
       this.address = address;
   }

}
