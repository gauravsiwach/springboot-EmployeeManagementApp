package com.example.EmployeeManagementApp.Entity;

import java.util.Set;

import com.example.EmployeeManagementApp.Security.Permission;

public enum Role {
    // ROLE_ADMIN,
    // ROLE_USER

    ROLE_ADMIN(Set.of(
            Permission.EMPLOYEE_READ,
            Permission.EMPLOYEE_CREATE,
            Permission.EMPLOYEE_UPDATE,
            Permission.EMPLOYEE_DELETE)
    ),

    ROLE_USER(Set.of(
            Permission.EMPLOYEE_READ)
    );

    private final Set<String> authorities;

    Role(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

}
