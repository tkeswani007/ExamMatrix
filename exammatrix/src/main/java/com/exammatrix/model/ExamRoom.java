package com.exammatrix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_rooms")
public class ExamRoom extends BaseEntity {

    @Column(nullable = false)
    private String roomName; 

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int roomRows;

    @Column(nullable = false)
    private int cols;

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getRoomRows() { return roomRows; }
    public void setRoomRows(int roomRows) { this.roomRows = roomRows; }

    public int getCols() { return cols; }
    public void setCols(int cols) { this.cols = cols; }

    public int getRows() { return roomRows; }
    public void setRows(int rows) { this.roomRows = rows; }
}