package com.exammatrix.controller;

import com.exammatrix.model.Student;
import com.exammatrix.repository.StudentRepository;
import com.exammatrix.repository.SeatAssignmentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    private final StudentRepository studentRepository;
    private final SeatAssignmentRepository seatAssignmentRepository;

    public StudentController(StudentRepository studentRepository, SeatAssignmentRepository seatAssignmentRepository) {
        this.studentRepository = studentRepository;
        this.seatAssignmentRepository = seatAssignmentRepository;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteStudent(@PathVariable Long id) {
        seatAssignmentRepository.deleteAll();
        studentRepository.deleteById(id);
    }
}