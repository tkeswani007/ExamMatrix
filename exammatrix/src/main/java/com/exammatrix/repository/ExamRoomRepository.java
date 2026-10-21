package com.exammatrix.repository;

import com.exammatrix.model.ExamRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRoomRepository extends JpaRepository<ExamRoom, Long> {
}