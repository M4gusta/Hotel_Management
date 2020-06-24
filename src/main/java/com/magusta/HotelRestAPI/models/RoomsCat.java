package com.magusta.HotelRestAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Entity
@Table(name = "Rooms_cats")
@JsonIgnoreProperties(value = {"createdDate", "modifiedDate"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class RoomsCat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int catId;

    @NotBlank
    @Column(nullable = false)
    private String nameOfCat;

    private boolean kitchen;

    private boolean jacuzzi;

    private boolean view;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date createdDate;

    @Column(nullable = false)
    @LastModifiedDate
    private Date modifiedDate;

    public RoomsCat(@NotBlank String nameOfCat, boolean kitchen) {
        this.nameOfCat = nameOfCat;
        this.kitchen = kitchen;
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

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getNameOfCat() {
        return nameOfCat;
    }

    public void setNameOfCat(String nameOfCat) {
        this.nameOfCat = nameOfCat;
    }

    public boolean isKitchen() {
        return kitchen;
    }

    public void setKitchen(boolean kitchen) {
        this.kitchen = kitchen;
    }

    public boolean isJacuzzi() {
        return jacuzzi;
    }

    public void setJacuzzi(boolean jacuzzi) {
        this.jacuzzi = jacuzzi;
    }

    public boolean isView() {
        return view;
    }

    public void setView(boolean view) {
        this.view = view;
    }
}
