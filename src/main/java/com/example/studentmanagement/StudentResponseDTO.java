package com.example.studentmanagement.dto;

public record StudentResponseDTO(
        Long id,
        String name,
        String email,
        String course,
        int age
) {
}