package com.magusta.HotelRestAPI.controllers;

import com.magusta.HotelRestAPI.models.RoomsCat;
import com.magusta.HotelRestAPI.repositories.RoomsCatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomsCatController {

    private final RoomsCatRepository roomsCatRepository;

    @Autowired
    public RoomsCatController(RoomsCatRepository roomsCatRepository) {
        this.roomsCatRepository = roomsCatRepository;
    }

    @GetMapping("/roomscat")
    public List<RoomsCat> getAllCategories(){
        return roomsCatRepository.findAll();
    }

    @GetMapping("/roomscat/{id}")
    public RoomsCat getCategoryById(@PathVariable(value = "id")int id){
        return roomsCatRepository.findById(id).orElseThrow();
    }

    @PostMapping("/roomscat")
    public void insertRoomsCategory(@Validated @RequestBody RoomsCat roomsCat){
        roomsCatRepository.save(roomsCat);
    }

    @PutMapping("/roomscat/{id}")
    public void updateRoomsCategory(@PathVariable(value = "id")int id, @Validated @RequestBody RoomsCat roomsCat){
        //RoomsCat roomsCat1 = roomsCatRepository.findById(id).orElseThrow();

        roomsCat.setCatId(id);

        roomsCatRepository.save(roomsCat);
    }

    @DeleteMapping("/roomscat/{id}")
    public ResponseEntity<?> deleteRoomsCategory(@PathVariable(value = "id")int id){
        RoomsCat roomsCat = roomsCatRepository.findById(id).orElseThrow();
        roomsCatRepository.delete(roomsCat);

        return ResponseEntity.ok().build();
    }

}
