package com.exammatrix.controller;

import com.exammatrix.service.AllocationService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/exam")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExamController {
    private final AllocationService allocationService;

    public ExamController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping("/allocate")
    public Map<String, Object> runAllocation() {
        return allocationService.allocateSeatsWithValidation();
    }

    @DeleteMapping("/reset")
    public Map<String, String> resetMatrix() {
        allocationService.resetAllocations();
        return Map.of("message", "Matrix reset successfully");
    }
}