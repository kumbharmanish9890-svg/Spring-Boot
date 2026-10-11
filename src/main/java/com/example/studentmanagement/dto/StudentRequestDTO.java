package com.example.studentmanagement.dto;

import jakarta.validation.constraints.*;

public record StudentRequestDTO(

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be 2 to 50 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email")
        String email,

        @NotBlank(message = "Course is required")
        String course,

        @Min(value = 15, message = "Age must be at least 15")
        @Max(value = 60, message = "Age must be at most 60")
        int age
) {
}