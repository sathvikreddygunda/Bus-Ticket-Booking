package com.apiimplementation.controller;


import com.apiimplementation.dto.ApplicationResponseDto;
import com.apiimplementation.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/applications")
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public String applyJob(@RequestParam Integer jobId,
                           Principal principal){

        return applicationService.applyJob(
                jobId,
                principal.getName()
        );
    }

    // View my applications (paginated)
    @GetMapping("/my")
    public Page<ApplicationResponseDto> getMyApplications(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return applicationService.getMyApplications(
                principal.getName(),
                PageRequest.of(page, size)
        );
    }



}
