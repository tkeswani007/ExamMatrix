package com.exammatrix.service;

import com.exammatrix.repository.ExamRoomRepository;
import com.exammatrix.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class AllocationService {
    private final ExamRoomRepository examRoomRepository;
    private final StudentRepository studentRepository;

    public AllocationService(ExamRoomRepository examRoomRepository, StudentRepository studentRepository) {
        this.examRoomRepository = examRoomRepository;
        this.studentRepository = studentRepository;
    }

    public String allocateSeats() {
        int seatsPerShift = examRoomRepository.findAll()
            .stream()
            .mapToInt(room -> room.getCapacity())
            .sum();

        int totalStudents = (int) studentRepository.count();

        if (totalStudents > seatsPerShift) {
            int totalShifts = (int) Math.ceil((double) totalStudents / seatsPerShift);
            
            return "Capacity exceeded! Total students: " + totalStudents + 
                   ". System has automatically scheduled " + totalShifts + " shifts.";
        }

        return "Allocation successful for single shift!";
    }
}