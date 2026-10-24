package com.exammatrix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_rooms")
public class ExamRoom extends BaseEntity {

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private int roomRows;

    @Column(nullable = false)
    private int cols;

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public int getRoomRows() { return roomRows; }
    public void setRoomRows(int roomRows) { this.roomRows = roomRows; }

    public int getCols() { return cols; }
    public void setCols(int cols) { this.cols = cols; }

    public int getRows() { return roomRows; }
    public void setRows(int rows) { this.roomRows = rows; }
}