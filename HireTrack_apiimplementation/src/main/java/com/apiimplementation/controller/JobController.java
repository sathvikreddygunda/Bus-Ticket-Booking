package com.apiimplementation.controller;


import com.apiimplementation.dto.CreateJobRequestDto;
import com.apiimplementation.dto.JobResponseDto;
import com.apiimplementation.service.JobService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/jobs")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public JobResponseDto createJob(@Valid
                                    @RequestBody
                                    CreateJobRequestDto dto,
                                    Principal principal){

        return jobService.createJob(
                dto,
                principal.getName()
        );
    }

    // pagination
    @GetMapping
    public Page<JobResponseDto> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        return jobService.getAllJobs(
                PageRequest.of(page, size)
        );
    }


}
