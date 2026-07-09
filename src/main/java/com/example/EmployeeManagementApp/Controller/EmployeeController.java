package com.example.EmployeeManagementApp.Controller;

import com.example.EmployeeManagementApp.Dto.ApiResponse;
import com.example.EmployeeManagementApp.Dto.EmployeeRequest;
import com.example.EmployeeManagementApp.Dto.EmployeeResponse;
import com.example.EmployeeManagementApp.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ApiResponse<List<EmployeeResponse>> getEmployees()
    {
        List<EmployeeResponse> employeeResponses = employeeService.getAllEmployee();
        return new ApiResponse<>(
            true,
            "Employees fetched successfully",
            employeeResponses);
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasRole('ADMIN') or returnObject.data.name == authentication.name")
    public ApiResponse<EmployeeResponse> getEmployeeById(@PathVariable Long id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeResponse employeeResponse = employeeService.getEmployeeById(id);
        if (authentication != null && employeeResponse != null) {
            System.out.println("========== POST-AUTHORIZE DEBUG ==========");
            System.out.println("LoggedIn Username (authentication.name): [" + authentication.getName() + "]");
            System.out.println("Employee Name (returnObject.data.name):   [" + employeeResponse.getName() + "]");
            System.out.println("Do they match?                           " + authentication.getName().equals(employeeResponse.getName()));
            System.out.println("==========================================");
        }
        return new ApiResponse<>(
            true,
            "Employee fetched successfully",
            employeeResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public  ApiResponse<EmployeeResponse> createEmployees(@Valid @RequestBody EmployeeRequest employee)
    {
        EmployeeResponse employeeResponse = employeeService.save(employee);
        return new ApiResponse<>(
            true, 
            "Employee created successfully", 
            employeeResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<EmployeeResponse> updateEmployee(@PathVariable Long id,  @RequestBody EmployeeRequest employee)
    {
        EmployeeResponse employeeResponse = employeeService.updateEmployee(id, employee);
        return new ApiResponse<>(
            true,
            "Employee updated successfully",
            employeeResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteEmployeeById(@PathVariable Long id)
    {
          employeeService.deleteEmployeeById(id);
          return new ApiResponse<>(
              true,
              "Employee deleted successfully",
              null);
    }
}
