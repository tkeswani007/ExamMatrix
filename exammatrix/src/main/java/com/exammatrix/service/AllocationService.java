package com.exammatrix.service;

import com.exammatrix.model.ExamRoom;
import com.exammatrix.model.SeatAssignment;
import com.exammatrix.model.Student;
import com.exammatrix.repository.ExamRoomRepository;
import com.exammatrix.repository.SeatAssignmentRepository;
import com.exammatrix.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AllocationService {
    private final ExamRoomRepository examRoomRepository;
    private final StudentRepository studentRepository;
    private final SeatAssignmentRepository seatAssignmentRepository;

    public AllocationService(ExamRoomRepository examRoomRepository, StudentRepository studentRepository, SeatAssignmentRepository seatAssignmentRepository) {
        this.examRoomRepository = examRoomRepository;
        this.studentRepository = studentRepository;
        this.seatAssignmentRepository = seatAssignmentRepository;
    }

    public Map<String, Object> allocateSeatsWithValidation() {
        seatAssignmentRepository.deleteAll();

        List<ExamRoom> rooms = examRoomRepository.findAll();
        List<Student> students = studentRepository.findAll();

        Map<String, Object> response = new HashMap<>();

        if (rooms.isEmpty() || students.isEmpty()) {
            response.put("success", false);
            response.put("message", "No rooms or students found.");
            response.put("allocations", new ArrayList<>());
            return response;
        }

        int totalCapacity = rooms.stream().mapToInt(room -> (room.getRows() > 0 ? room.getRows() : room.getRoomRows()) * room.getCols()).sum();
        if (students.size() > totalCapacity) {
            response.put("success", false);
            response.put("message", "Capacity Overflow! Total seats (" + totalCapacity + ") are less than registered students (" + students.size() + ").");
            response.put("allocations", new ArrayList<>());
            return response;
        }

        List<SeatAssignment> newAssignments = new ArrayList<>();
        int studentIndex = 0;

        for (ExamRoom room : rooms) {
            int rows = room.getRows() > 0 ? room.getRows() : room.getRoomRows();
            int cols = room.getCols();

            for (int r = 1; r <= rows; r++) {
                for (int c = 1; c <= cols; c++) {
                    if (studentIndex >= students.size()) break;
                    Student student = students.get(studentIndex++);

                    SeatAssignment assignment = new SeatAssignment();
                    assignment.setExamRoom(room);
                    assignment.setStudent(student);
                    assignment.setSeatRow(r);
                    assignment.setSeatCol(c);

                    newAssignments.add(assignment);
                }
                if (studentIndex >= students.size()) break;
            }
        }

        seatAssignmentRepository.saveAll(newAssignments);

        List<Map<String, Object>> result = new ArrayList<>();
        for (SeatAssignment assignment : newAssignments) {
            Map<String, Object> map = new HashMap<>();
            map.put("examRoom", assignment.getExamRoom());
            map.put("student", assignment.getStudent());
            map.put("seatRow", assignment.getSeatRow());
            map.put("seatCol", assignment.getSeatCol());
            result.add(map);
        }

        response.put("success", true);
        response.put("message", "Allocation successful!");
        response.put("allocations", result);
        return response;
    }

    public List<Map<String, Object>> allocateSeats() {
        Map<String, Object> res = allocateSeatsWithValidation();
        if (res.get("success") == Boolean.FALSE) {
            List<Map<String, Object>> errList = new ArrayList<>();
            Map<String, Object> errMap = new HashMap<>();
            errMap.put("success", false);
            errMap.put("message", res.get("message"));
            errList.add(errMap);
            return errList;
        }
        return (List<Map<String, Object>>) res.get("allocations");
    }

    public void resetAllocations() {
        seatAssignmentRepository.deleteAll();
    }
}