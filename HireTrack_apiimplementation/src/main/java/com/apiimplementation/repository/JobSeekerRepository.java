package com.apiimplementation.repository;

import com.apiimplementation.model.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import com.apiimplementation.model.User;

import java.util.Optional;


public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> {
    Optional<JobSeeker> findByUser(User user);
}
