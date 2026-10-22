package com.exammatrix.service;

import org.springframework.stereotype.Service;

@Service
public class AllocationService {

    private final AllocationEngine allocationEngine;

    public AllocationService(AllocationEngine allocationEngine) {
        this.allocationEngine = allocationEngine;
    }

    public String allocateSeats() {
        return allocationEngine.allocateSeats();
    }
}