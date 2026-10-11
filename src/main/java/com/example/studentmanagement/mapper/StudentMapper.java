package com.example.studentmanagement.mapper;

import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.entity.Student;

public class StudentMapper {

    // DTO -> Entity (database ला पाठवण्यासाठी)
    public static Student toEntity(StudentRequestDTO dto) {
        Student student = new Student();
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setCourse(dto.course());
        student.setAge(dto.age());
        return student;
    }

    // Entity -> DTO (client ला पाठवण्यासाठी)
    public static StudentResponseDTO toResponse(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourse(),
                student.getAge()
        );
    }
}