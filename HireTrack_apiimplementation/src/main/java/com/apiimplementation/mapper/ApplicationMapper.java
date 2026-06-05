package com.apiimplementation.mapper;

import com.apiimplementation.dto.ApplicationResponseDto;
import com.apiimplementation.model.Application;

public class ApplicationMapper {

    // Convert Application Entity -> ApplicationResponseDto
    public static ApplicationResponseDto convertToDto(
            Application application) {

        return new ApplicationResponseDto(
                application.getId(),
                application.getAppliedAt(),
                application.getJob().getTitle(),
                application.getJob()
                        .getEmployer()
                        .getCompanyName()
        );
    }
}