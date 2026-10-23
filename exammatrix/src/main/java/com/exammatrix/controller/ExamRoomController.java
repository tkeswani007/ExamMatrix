package com.exammatrix.controller;

import com.exammatrix.model.ExamRoom;
import com.exammatrix.repository.ExamRoomRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class ExamRoomController {
    private final ExamRoomRepository examRoomRepository;

    public ExamRoomController(ExamRoomRepository examRoomRepository) {
        this.examRoomRepository = examRoomRepository;
    }

    @PostMapping
    public ExamRoom createRoom(@RequestBody ExamRoom room) {
        return examRoomRepository.save(room);
    }

    @GetMapping
    public List<ExamRoom> getAllRooms() {
        return examRoomRepository.findAll();
    }
}