package com.exammatrix.service;

import com.exammatrix.model.ExamRoom;
import com.exammatrix.model.Student;
import com.exammatrix.model.SeatAssignment;
import java.util.List;

public interface AllocationStrategy {
    List<SeatAssignment> allocateSeats(List<ExamRoom> rooms, List<Student> students);
}