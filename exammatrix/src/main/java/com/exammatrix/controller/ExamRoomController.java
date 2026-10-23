package com.exammatrix.controller;

import com.exammatrix.model.ExamRoom;
import com.exammatrix.repository.ExamRoomRepository;
import com.exammatrix.repository.SeatAssignmentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class ExamRoomController {
    private final ExamRoomRepository examRoomRepository;
    private final SeatAssignmentRepository seatAssignmentRepository;

    public ExamRoomController(ExamRoomRepository examRoomRepository, SeatAssignmentRepository seatAssignmentRepository) {
        this.examRoomRepository = examRoomRepository;
        this.seatAssignmentRepository = seatAssignmentRepository;
    }

    @PostMapping
    public ExamRoom createRoom(@RequestBody ExamRoom room) {
        return examRoomRepository.save(room);
    }

    @GetMapping
    public List<ExamRoom> getAllRooms() {
        return examRoomRepository.findAll();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteRoom(@PathVariable Long id) {
        seatAssignmentRepository.deleteAll();
        examRoomRepository.deleteById(id);
    }
}