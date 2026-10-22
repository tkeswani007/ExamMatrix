package com.exammatrix.service;

import com.exammatrix.model.ExamRoom;
import com.exammatrix.model.SeatAssignment;
import com.exammatrix.model.Student;
import com.exammatrix.repository.ExamRoomRepository;
import com.exammatrix.repository.SeatAssignmentRepository;
import com.exammatrix.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Transactional
    public String allocateSeats() {
        seatAssignmentRepository.deleteAll();

        List<Student> students = studentRepository.findAll();
        List<ExamRoom> rooms = examRoomRepository.findAll();

        if (students.isEmpty() || rooms.isEmpty()) {
            return "Error: Please add both students and exam rooms before allocating!";
        }

        Map<String, List<Student>> departmentMap = new HashMap<>();
        for (Student student : students) {
            String dept = student.getDepartment();
            if (!departmentMap.containsKey(dept)) {
                departmentMap.put(dept, new ArrayList<>());
            }
            departmentMap.get(dept).add(student);
        }

        List<Student> interleavedStudents = new ArrayList<>();
        List<Iterator<Student>> iterators = new ArrayList<>();
        for (List<Student> deptStudents : departmentMap.values()) {
            iterators.add(deptStudents.iterator());
        }

        boolean hasMore = true;
        while (hasMore) {
            hasMore = false;
            for (Iterator<Student> iterator : iterators) {
                if (iterator.hasNext()) {
                    interleavedStudents.add(iterator.next());
                    hasMore = true;
                }
            }
        }

        int studentIndex = 0;
        List<SeatAssignment> finalAssignments = new ArrayList<>();

        for (ExamRoom room : rooms) {
            int roomRows = room.getRows();
            int roomCols = room.getCols();

            for (int r = 1; r <= roomRows; r++) {
                for (int c = 1; c <= roomCols; c++) {
                    if (studentIndex >= interleavedStudents.size()) {
                        break;
                    }

                    Student assignedStudent = interleavedStudents.get(studentIndex++);
                    
                    SeatAssignment assignment = new SeatAssignment();
                    assignment.setExamRoom(room);
                    assignment.setStudent(assignedStudent);
                    assignment.setSeatRow(r);
                    assignment.setSeatCol(c);

                    finalAssignments.add(assignment);
                }
                if (studentIndex >= interleavedStudents.size()) {
                    break;
                }
            }
            if (studentIndex >= interleavedStudents.size()) {
                break;
            }
        }

        seatAssignmentRepository.saveAll(finalAssignments);
        return "Success: Allocated " + finalAssignments.size() + " students with anti-cheating department spacing!";
    }
}