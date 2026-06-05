package com.apiimplementation.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApplicationResponseDto {

    private Integer id;
    private LocalDateTime appliedAt;
    private String jobTitle;
    private String companyName;

}
