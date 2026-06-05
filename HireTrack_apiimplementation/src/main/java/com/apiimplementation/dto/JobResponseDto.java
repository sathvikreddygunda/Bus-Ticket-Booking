package com.apiimplementation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobResponseDto {

    private Integer id;
    private String title;
    private String location;
    private Double salary;
    private String companyName;
}
