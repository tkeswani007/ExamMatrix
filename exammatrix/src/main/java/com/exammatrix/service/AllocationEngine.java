package com.exammatrix.service;

import com.exammatrix.model.ExamRoom;
import com.exammatrix.model.SeatAssignment;
import com.exammatrix.model.Student;
import com.exammatrix.repository.ExamRoomRepository;
import com.exammatrix.repository.SeatAssignmentRepository;
import com.exammatrix.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllocationEngine {

    private final StudentRepository studentRepository;
    private final ExamRoomRepository examRoomRepository;
    private final SeatAssignmentRepository seatAssignmentRepository;

    public AllocationEngine(StudentRepository studentRepository,
                            ExamRoomRepository examRoomRepository,
                            SeatAssignmentRepository seatAssignmentRepository) {
        this.studentRepository = studentRepository;
        this.examRoomRepository = examRoomRepository;
        this.seatAssignmentRepository = seatAssignmentRepository;
    }

    public String generateAllocations() {
        seatAssignmentRepository.deleteAll();

        List<ExamRoom> rooms = examRoomRepository.findAll();
        List<Student> students = studentRepository.findAll();

        if (rooms.isEmpty()) {
            return "Error: No exam rooms found in the database!";
        }
        if (students.isEmpty()) {
            return "Error: No students found in the database!";
        }

        int studentIndex = 0;

        for (ExamRoom room : rooms) {
            int roomRows = room.getRows();
            int roomCols = room.getCols();

            for (int r = 1; r <= roomRows; r++) {
                for (int c = 1; c <= roomCols; c++) {
                    if (studentIndex >= students.size()) {
                        return "Allocation completed successfully! Seated " + studentIndex + " students.";
                    }

                    Student student = students.get(studentIndex++);

                    SeatAssignment assignment = new SeatAssignment();
                    assignment.setStudent(student);
                    assignment.setExamRoom(room);
                    assignment.setSeatRow(r);
                    assignment.setSeatCol(c);

                    seatAssignmentRepository.save(assignment);
                }
            }
        }

        return "Allocation completed successfully! Seated " + studentIndex + " students (some rooms may have remaining space).";
    }
}