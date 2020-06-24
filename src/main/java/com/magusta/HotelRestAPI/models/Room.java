package com.magusta.HotelRestAPI.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "rooms")
//createdDate i modifiedDate są ignorowane przy POST i PUT, jedynie są dołączane przy GET
@JsonIgnoreProperties(value = {"createdDate", "modifiedDate"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Room {

    @Id
    private int roomNum;

    @NotBlank
    private String roomCat;

    private boolean occupied;

    private int space;

    private int beds;

    private int floor;

    private int prize;

    @Column(nullable = false, updatable = false)
    //Dodaje datę stworzenia
    @CreatedDate
    private Date createdDate;

    @Column(nullable = false)
    @LastModifiedDate
    private Date modifiedDate;

    public Room(int roomNum, @NotBlank String roomCat, int space) {
        this.roomNum = roomNum;
        this.roomCat = roomCat;
        this.space = space;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getRoomCat() {
        return roomCat;
    }

    public void setRoomCat(String roomCat) {
        this.roomCat = roomCat;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }
}
