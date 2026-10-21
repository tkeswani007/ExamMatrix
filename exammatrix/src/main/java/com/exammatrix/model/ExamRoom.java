package com.exammatrix.model;
import jakarta.persistence.*;

@Entity
@Table(name = "exam_rooms")

public class ExamRoom {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;
    private int capacity;

    @Column(name = "room_rows")
    private int rows;
    private int cols;

    public ExamRoom() {}

    public ExamRoom(String roomName, int capacity, int rows, int cols) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.rows = rows;
        this.cols = cols;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getRoomName(){return roomName;}
    public void setRoomName(String roomName){this.roomName = roomName;}

    public int getCapacity(){return capacity;}
    public void setCapacity(int capacity){this.capacity = capacity;}

    public int getRows(){return rows;}
    public void setRows(int rows){this.rows = rows;}

    public int getCols(){return cols;}
    public void setCols(int cols){this.cols = cols;}

}
