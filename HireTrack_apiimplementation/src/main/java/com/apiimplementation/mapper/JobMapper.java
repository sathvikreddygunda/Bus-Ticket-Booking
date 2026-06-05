package com.apiimplementation.mapper;

import com.apiimplementation.dto.CreateJobRequestDto;
import com.apiimplementation.dto.JobResponseDto;
import com.apiimplementation.model.Employer;
import com.apiimplementation.model.Job;

public class JobMapper {

    public static Job convertToJob(
            CreateJobRequestDto dto,
            Employer employer){

        Job job = new Job();

        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setSalary(dto.getSalary());
        job.setEmployer(employer);

        return job;
    }

    public static JobResponseDto convertToDto(
            Job job){

        return new JobResponseDto(
                job.getId(),
                job.getTitle(),
                job.getLocation(),
                job.getSalary(),
                job.getEmployer().getCompanyName()
        );
    }
}