
        /*package com.example.studentmanagement.controller;

        import com.example.studentmanagement.entity.Student;
        import com.example.studentmanagement.service.StudentService;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

        @RestController
        @RequestMapping("/api/students")
        public class StudentController {

            private final StudentService studentService;

            public StudentController(StudentService studentService) {
                this.studentService = studentService;
            }

            @PostMapping
            public Student create(@RequestBody Student student) {
                return studentService.createStudent(student);
            }

            @GetMapping
            public List<Student> getAll() {
                return studentService.getAllStudents();
            }

            @GetMapping("/{id}")
            public Student getById(@PathVariable Long id) {
                return studentService.getStudentById(id);
            }

            @PutMapping("/{id}")
            public Student update(@PathVariable Long id, @RequestBody Student student) {
                return studentService.updateStudent(id, student);
            }

            @DeleteMapping("/{id}")
            public String delete(@PathVariable Long id) {
                studentService.deleteStudent(id);
                return "Student deleted successfully";
            }
        }*/
        package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.studentmanagement.dto.PageResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDTO create(@Valid @RequestBody StudentRequestDTO dto) {
        return studentService.createStudent(dto);
    }

    @GetMapping
    public PageResponseDTO<StudentResponseDTO> search(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String course,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return studentService.searchStudents(name, course, page, size, sortBy, direction);
    }

    @GetMapping("/{id}")
    public StudentResponseDTO getById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public StudentResponseDTO update(@PathVariable Long id,
                                     @Valid @RequestBody StudentRequestDTO dto) {
        return studentService.updateStudent(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully";
    }
}