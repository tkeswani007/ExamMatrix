package com.exammatrix.controller;

import com.exammatrix.model.ExamRoom;
import com.exammatrix.model.SeatAssignment;
import com.exammatrix.model.Student;
import com.exammatrix.repository.ExamRoomRepository;
import com.exammatrix.repository.StudentRepository;
import com.exammatrix.service.AllocationStrategy;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exam")
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamRoomRepository roomRepository;
    private final StudentRepository studentRepository;
    private final AllocationStrategy allocationStrategy; // Polymorphism via Interface

    public ExamController(ExamRoomRepository roomRepository, StudentRepository studentRepository, AllocationStrategy allocationStrategy) {
        this.roomRepository = roomRepository;
        this.studentRepository = studentRepository;
        this.allocationStrategy = allocationStrategy;
    }

    @PostMapping("/allocate")
    public Object allocateSeats() {
        List<ExamRoom> rooms = roomRepository.findAll();
        List<Student> students = studentRepository.findAll();

        // Calculate total capacity
        int totalCapacity = rooms.stream().mapToInt(r -> r.getRoomRows() * r.getCols()).sum();
        
        if (students.size() > totalCapacity) {
            return Map.of(
                "success", false, 
                "message", "Capacity overflow! Total students (" + students.size() + ") exceed total room capacity (" + totalCapacity + ")."
            );
        }

        // Use the injected strategy interface implementation
        List<SeatAssignment> assignments = allocationStrategy.allocateSeats(rooms, students);
        
        return Map.of(
            "success", true,
            "allocations", assignments
        );
    }
}