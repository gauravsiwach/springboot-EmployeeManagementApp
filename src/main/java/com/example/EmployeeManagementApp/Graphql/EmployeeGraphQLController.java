package com.example.EmployeeManagementApp.Graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.List;

import com.example.EmployeeManagementApp.Dto.EmployeeResponse;
import com.example.EmployeeManagementApp.Service.EmployeeService;

@Controller
public class EmployeeGraphQLController {
    
    @Autowired
    private EmployeeService employeeService;



    @QueryMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_READ')")
    public List<EmployeeResponse> employees() {
        return employeeService.getAllEmployee();
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_READ')")
    public EmployeeResponse employeeById(@Argument("id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_CREATE')")
    public EmployeeResponse createEmployee(@Argument("employee") EmployeeInput employeeInput) {
        return employeeService.save(employeeInput);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_UPDATE')")
    public EmployeeResponse updateEmployee(@Argument("id") Long id, @Argument("employee") EmployeeInput employeeInput) {
        return employeeService.updateEmployee(id, employeeInput);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('EMPLOYEE_DELETE')")
    public String deleteEmployee(@Argument("id") Long id) {
        employeeService.deleteEmployeeById(id);
        return "Employee with id " + id + " deleted successfully";
    }




    

}
