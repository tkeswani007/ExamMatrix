package com.exammatrix.service;

import com.exammatrix.model.ExamRoom;
import com.exammatrix.model.Student;
import com.exammatrix.model.SeatAssignment;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class StandardAllocationStrategy implements AllocationStrategy {

    @Override
    public List<SeatAssignment> allocateSeats(List<ExamRoom> rooms, List<Student> students) {
        List<SeatAssignment> assignments = new ArrayList<>();
        int studentIndex = 0;

        for (ExamRoom room : rooms) {
            int rows = room.getRoomRows();
            int cols = room.getCols();

            for (int r = 1; r <= rows; r++) {
                for (int c = 1; c <= cols; c++) {
                    if (studentIndex < students.size()) {
                        Student student = students.get(studentIndex++);
                        SeatAssignment assignment = new SeatAssignment();
                        assignment.setExamRoom(room);
                        assignment.setStudent(student);
                        assignment.setSeatRow(r);
                        assignment.setSeatCol(c);
                        assignments.add(assignment);
                    }
                }
            }
        }
        return assignments;
    }
}