package com.exammatrix.repository;

import com.exammatrix.model.SeatAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatAssignmentRepository extends JpaRepository<SeatAssignment, Long> {
}