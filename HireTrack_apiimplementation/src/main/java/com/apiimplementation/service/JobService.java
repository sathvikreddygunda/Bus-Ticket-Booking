package com.apiimplementation.service;

import com.apiimplementation.dto.CreateJobRequestDto;
import com.apiimplementation.dto.JobResponseDto;
import com.apiimplementation.mapper.JobMapper;
import com.apiimplementation.model.Employer;
import com.apiimplementation.model.Job;
import com.apiimplementation.model.User;
import com.apiimplementation.repository.EmployerRepository;
import com.apiimplementation.repository.JobRepository;
import com.apiimplementation.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final UserRepository userRepository;

    // Employer posts a job
    public JobResponseDto createJob(
            CreateJobRequestDto dto,
            String username) {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        Employer employer = employerRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Employer Not Found"));

        Job job = JobMapper.convertToJob(dto, employer);

        Job savedJob = jobRepository.save(job);

        return JobMapper.convertToDto(savedJob);
    }

    // Get all jobs
    public Page<JobResponseDto> getAllJobs(Pageable pageable) {

        Page<Job> jobs = jobRepository.findAll(pageable);

        return jobs.map(JobMapper::convertToDto);
    }

    // Get one job
    public Job getById(Integer id) {

        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job Not Found"));
    }
}