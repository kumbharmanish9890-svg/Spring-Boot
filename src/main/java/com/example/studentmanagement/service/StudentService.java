package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.PageResponseDTO;
import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.mapper.StudentMapper;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    private static final Set<String> SORT_FIELDS = Set.of("id", "name", "email", "course", "age");

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentResponseDTO createStudent(StudentRequestDTO dto) {
        Student student = StudentMapper.toEntity(dto);
        Student saved = studentRepository.save(student);
        return StudentMapper.toResponse(saved);
    }

    public PageResponseDTO<StudentResponseDTO> searchStudents(
            String name, String course, int page, int size, String sortBy, String direction) {

        if (!SORT_FIELDS.contains(sortBy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "sortBy must be one of: id, name, email, course, age");
        }

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 50);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(safePage, safeSize, sort);

        Page<Student> result = studentRepository
                .findByNameContainingIgnoreCaseAndCourseContainingIgnoreCase(name, course, pageable);

        List<StudentResponseDTO> content = result.getContent()
                .stream()
                .map(StudentMapper::toResponse)
                .toList();

        return new PageResponseDTO<>(
                content,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast());
    }

    public StudentResponseDTO getStudentById(Long id) {
        return StudentMapper.toResponse(findStudent(id));
    }

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO dto) {
        Student student = findStudent(id);
        student.setName(dto.name());
        student.setEmail(dto.email());
        student.setCourse(dto.course());
        student.setAge(dto.age());
        Student saved = studentRepository.save(student);
        return StudentMapper.toResponse(saved);
    }

    public void deleteStudent(Long id) {
        studentRepository.delete(findStudent(id));
    }

    private Student findStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student not found with id " + id));
    }
}