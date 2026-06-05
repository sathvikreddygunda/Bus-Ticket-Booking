package com.apiimplementation.repository;

import com.apiimplementation.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import com.apiimplementation.model.User;

import java.util.Optional;


public interface EmployerRepository extends JpaRepository<Employer, Integer> {
    Optional<Employer> findByUser(User user);
}
