package com.example.EmployeeManagementApp.Security;

public class Permission {
    
    private Permission() {
        // Private constructor to prevent instantiation
    }

    public static final String EMPLOYEE_READ = "EMPLOYEE_READ";
    public static final String EMPLOYEE_CREATE = "EMPLOYEE_CREATE";
    public static final String EMPLOYEE_UPDATE = "EMPLOYEE_UPDATE";
    public static final String EMPLOYEE_DELETE = "EMPLOYEE_DELETE";

}
