package com.apiimplementation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CreateBookDto {

    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Summary is required")
    private String summary;
}
