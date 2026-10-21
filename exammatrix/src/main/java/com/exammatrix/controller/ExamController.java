package com.exammatrix.controller;

import com.exammatrix.service.AllocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    private final AllocationService allocationService;

    public ExamController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @GetMapping("/allocate")
    public String runAllocation() {
        return allocationService.allocateSeats();
    }
}