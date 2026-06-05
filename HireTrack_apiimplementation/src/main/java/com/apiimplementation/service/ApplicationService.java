package com.apiimplementation.service;

import com.apiimplementation.dto.ApplicationResponseDto;
import com.apiimplementation.mapper.ApplicationMapper;
import com.apiimplementation.model.Application;
import com.apiimplementation.model.Job;
import com.apiimplementation.model.JobSeeker;
import com.apiimplementation.model.User;
import com.apiimplementation.repository.ApplicationRepository;
import com.apiimplementation.repository.JobRepository;
import com.apiimplementation.repository.JobSeekerRepository;
import com.apiimplementation.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final UserRepository userRepository;

    // Apply for a job
    public String applyJob(
            Integer jobId,
            String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        JobSeeker jobSeeker = jobSeekerRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Job Seeker Not Found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job Not Found"));

        Application application = new Application();

        application.setAppliedAt(LocalDateTime.now());
        application.setJob(job);
        application.setJobSeeker(jobSeeker);

        applicationRepository.save(application);

        return "Application Submitted Successfully";
    }

    // View my applications
    public Page<ApplicationResponseDto> getMyApplications(
            String username,
            Pageable pageable) {

        return applicationRepository
                .findAll(pageable)
                .map(ApplicationMapper::convertToDto);
    }
}