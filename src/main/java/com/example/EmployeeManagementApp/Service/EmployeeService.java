package com.example.EmployeeManagementApp.Service;

import com.example.EmployeeManagementApp.Entity.Address;
import com.example.EmployeeManagementApp.Entity.Employee;
import com.example.EmployeeManagementApp.Exception.EmployeeNotFoundException;
import com.example.EmployeeManagementApp.Graphql.EmployeeInput;
import com.example.EmployeeManagementApp.Dto.EmployeeRequest;
import com.example.EmployeeManagementApp.Dto.EmployeeResponse;
import com.example.EmployeeManagementApp.Dto.AddressResponse;
import com.example.EmployeeManagementApp.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeResponse> getAllEmployee()
    {
        logger.info("Fetching all employees");
        List<Employee> employees = employeeRepository.findAll();
        logger.info("Found {} employees", employees.size());
        return employees.stream()
                .map(employee -> new EmployeeResponse(
                    employee.getEmployeeId(), 
                    employee.getName(), 
                    employee.getEmail(), 
                    employee.getSalary(), 
                    employee.getDesignation(),
        
                    employee.getAddress() != null ?
                    new AddressResponse(
                        employee.getAddress().getAddressId(),
                        employee.getAddress().getStreet(),
                        employee.getAddress().getCity(),
                        employee.getAddress().getState(),
                        employee.getAddress().getCountry(),
                        employee.getAddress().getZipCode()
                    ) : null
                ))
                .toList();
    }

    public EmployeeResponse save(EmployeeRequest employeeRequest)
    {
        logger.info("Saving employee: {}", employeeRequest);
        Employee employee= new Employee();
        employee.setName(employeeRequest.getName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setSalary(employeeRequest.getSalary());
        employee.setDesignation(employeeRequest.getDesignation());
       
        Address address = new Address();
        address.setCity(employeeRequest.getAddress().getCity());
        address.setState(employeeRequest.getAddress().getState());
        address.setCountry(employeeRequest.getAddress().getCountry());
        address.setZipCode(employeeRequest.getAddress().getZipCode());

        employee.setAddress(address);
        

        Employee savedEmployee = employeeRepository.save(employee);
        logger.info("Employee saved with id: {}", savedEmployee.getEmployeeId());
        AddressResponse addressResponse = savedEmployee.getAddress() != null ? new AddressResponse(
            savedEmployee.getAddress().getAddressId(),
            savedEmployee.getAddress().getStreet(),
            savedEmployee.getAddress().getCity(),
            savedEmployee.getAddress().getState(),
            savedEmployee.getAddress().getCountry(),
            savedEmployee.getAddress().getZipCode()
        ) : null;
        return new EmployeeResponse(savedEmployee.getEmployeeId(), savedEmployee.getName(), savedEmployee.getEmail(), savedEmployee.getSalary(), savedEmployee.getDesignation(), addressResponse);
    }

    //this is a override method is used to save employee from graphql controller
    public EmployeeResponse save(EmployeeInput employeeRequest)
    {
        logger.info("Saving employee: {}", employeeRequest);
        Employee employee= new Employee();
        employee.setName(employeeRequest.getName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setSalary(employeeRequest.getSalary());
        employee.setDesignation(employeeRequest.getDesignation());
    
        Employee savedEmployee = employeeRepository.save(employee);
        logger.info("Employee saved with id: {}", savedEmployee.getEmployeeId());
        AddressResponse addressResponse = savedEmployee.getAddress() != null ? new AddressResponse(
            savedEmployee.getAddress().getAddressId(),
            savedEmployee.getAddress().getStreet(),
            savedEmployee.getAddress().getCity(),
            savedEmployee.getAddress().getState(),
            savedEmployee.getAddress().getCountry(),
            savedEmployee.getAddress().getZipCode()
        ) : null;
        return new EmployeeResponse(savedEmployee.getEmployeeId(), savedEmployee.getName(), savedEmployee.getEmail(), savedEmployee.getSalary(), savedEmployee.getDesignation(), addressResponse);
    }

 

    public EmployeeResponse getEmployeeById(Long id)
    {
        logger.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));
        logger.info("Found employee: {}", employee);
        AddressResponse addressResponse = employee.getAddress() != null ? new AddressResponse(
            employee.getAddress().getAddressId(),
            employee.getAddress().getStreet(),
            employee.getAddress().getCity(),
            employee.getAddress().getState(),
            employee.getAddress().getCountry(),
            employee.getAddress().getZipCode()
        ) : null;
        return new EmployeeResponse(employee.getEmployeeId(), employee.getName(), employee.getEmail(), employee.getSalary(), employee.getDesignation(), addressResponse);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest updateEmployeeRequest)
    {
        logger.info("Updating employee with id: {}", id);
        Employee employee= employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));
        employee.setName(updateEmployeeRequest.getName());
        employee.setDesignation(updateEmployeeRequest.getDesignation());
        employee.setEmail(updateEmployeeRequest.getEmail());
        employee.setSalary(updateEmployeeRequest.getSalary());
        Employee updatedEmployee = employeeRepository.save(employee);
        logger.info("Updated employee: {}", updatedEmployee);
        AddressResponse addressResponse = updatedEmployee.getAddress() != null ? new AddressResponse(
            updatedEmployee.getAddress().getAddressId(),
            updatedEmployee.getAddress().getStreet(),
            updatedEmployee.getAddress().getCity(),
            updatedEmployee.getAddress().getState(),
            updatedEmployee.getAddress().getCountry(),
            updatedEmployee.getAddress().getZipCode()
        ) : null;
        return new EmployeeResponse(updatedEmployee.getEmployeeId(), updatedEmployee.getName(), updatedEmployee.getEmail(), updatedEmployee.getSalary(), updatedEmployee.getDesignation(), addressResponse);
    }

    //this is a override method is used to update employee from graphql controller
    public EmployeeResponse updateEmployee(Long id, EmployeeInput updateEmployeeRequest)
    {
        logger.info("Updating employee with id: {}", id);
        Employee employee= employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + id + " not found"));
        employee.setName(updateEmployeeRequest.getName());
        employee.setDesignation(updateEmployeeRequest.getDesignation());
        employee.setEmail(updateEmployeeRequest.getEmail());
        employee.setSalary(updateEmployeeRequest.getSalary());
        Employee updatedEmployee = employeeRepository.save(employee);
        logger.info("Updated employee: {}", updatedEmployee);
        return new EmployeeResponse(updatedEmployee.getEmployeeId(), updatedEmployee.getName(), updatedEmployee.getEmail(), updatedEmployee.getSalary(), updatedEmployee.getDesignation(), null);
    }

    public void deleteEmployeeById(Long id)
    {    logger.info("Deleting employee with id: {}", id);
         employeeRepository.deleteById(id);
         logger.info("Deleted employee with id: {}", id);
    }


}
