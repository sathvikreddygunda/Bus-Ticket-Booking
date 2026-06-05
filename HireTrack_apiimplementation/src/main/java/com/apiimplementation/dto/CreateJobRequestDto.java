package com.apiimplementation.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateJobRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String location;

    @NotNull
    private Double salary;
}
