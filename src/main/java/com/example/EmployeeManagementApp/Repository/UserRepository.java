package com.example.EmployeeManagementApp.Repository;

import com.example.EmployeeManagementApp.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    
    Optional<AppUser> findByUsername(String username);
    
}
