package com.example.EmployeeManagementApp.Controller;

import com.example.EmployeeManagementApp.Dto.ApiResponse;
import com.example.EmployeeManagementApp.Dto.EmployeeRequest;
import com.example.EmployeeManagementApp.Dto.EmployeeResponse;
import com.example.EmployeeManagementApp.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ApiResponse<List<EmployeeResponse>> getEmployees()
    {
        List<EmployeeResponse> employeeResponses = employeeService.getAllEmployee();
        return new ApiResponse<>(
            true,
            "Employees fetched successfully",
            employeeResponses);
    }

    @GetMapping("/{id}")
    public ApiResponse<EmployeeResponse> getEmployeeById(@PathVariable Long id)
    {
        EmployeeResponse employeeResponse = employeeService.getEmployeeById(id);
        return new ApiResponse<>(
            true,
            "Employee fetched successfully",
            employeeResponse);
    }

    @PostMapping
    public  ApiResponse<EmployeeResponse> createEmployees(@Valid @RequestBody EmployeeRequest employee)
    {
        EmployeeResponse employeeResponse = employeeService.save(employee);
        return new ApiResponse<>(
            true, 
            "Employee created successfully", 
            employeeResponse);
    }

    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponse> updateEmployee(@PathVariable Long id,  @RequestBody EmployeeRequest employee)
    {
        EmployeeResponse employeeResponse = employeeService.updateEmployee(id, employee);
        return new ApiResponse<>(
            true,
            "Employee updated successfully",
            employeeResponse);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteEmployeeById(@PathVariable Long id)
    {
          employeeService.deleteEmployeeById(id);
          return new ApiResponse<>(
              true,
              "Employee deleted successfully",
              null);
    }
}
