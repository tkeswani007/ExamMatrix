package com.exammatrix.service;

import com.exammatrix.model.ExamRoom;
import com.exammatrix.model.SeatAssignment;
import com.exammatrix.model.Student;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AllocationStrategyTest {
    private final AllocationStrategy strategy = new StandardAllocationStrategy();

    @Test
    public void testAllocateSeatsSuccess() {
        ExamRoom r = new ExamRoom();
        r.setRoomRows(2);
        r.setCols(2);
        Student s = new Student();
        s.setName("John");
        List<SeatAssignment> res = strategy.allocateSeats(List.of(r), List.of(s));
        assertEquals(1, res.size());
        assertEquals("John", res.get(0).getStudent().getName());
    }

    @Test
    public void testAllocateSeatsEmptyStudents() {
        ExamRoom r = new ExamRoom();
        r.setRoomRows(2);
        r.setCols(2);
        assertTrue(strategy.allocateSeats(List.of(r), List.of()).isEmpty());
    }

    @Test
    public void testAllocateSeatsEmptyRooms() {
        Student s = new Student();
        assertTrue(strategy.allocateSeats(List.of(), List.of(s)).isEmpty());
    }

    @Test
    public void testStudentEncapsulation() {
        Student s = new Student();
        s.setName("Alice");
        s.setRollNumber("R002");
        assertEquals("Alice", s.getName());
        assertEquals("R002", s.getRollNumber());
    }

    @Test
    public void testExamRoomCapacity() {
        ExamRoom r = new ExamRoom();
        r.setRoomRows(5);
        r.setCols(6);
        assertEquals(30, r.getRoomRows() * r.getCols());
    }
}