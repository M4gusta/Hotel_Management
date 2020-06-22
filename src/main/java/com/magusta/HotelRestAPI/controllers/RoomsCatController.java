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
    public List<RoomsCat> findAll(){
        return roomsCatRepository.findAll();
    }

    @GetMapping("/roomscat/{id}")
    public RoomsCat findById(@PathVariable(value = "id")int id){
        return roomsCatRepository.findById(id).orElseThrow();
    }

    @PostMapping("/roomscat")
    public RoomsCat insertRoomsCat(@Validated @RequestBody RoomsCat roomsCat){
        return roomsCatRepository.save(roomsCat);
    }

    @PutMapping("/roomscat/{id}")
    public RoomsCat updateRoomsCat(@PathVariable(value = "id")int id, @Validated @RequestBody RoomsCat roomsCat){
        RoomsCat roomsCat1 = roomsCatRepository.findById(id).orElseThrow();

        roomsCat.setCatId(roomsCat1.getCatId());

        return roomsCatRepository.save(roomsCat);
    }

    @DeleteMapping("/roomscat/{id}")
    public ResponseEntity<?> deleteRoomsCat(@PathVariable(value = "id")int id){
        RoomsCat roomsCat = roomsCatRepository.findById(id).orElseThrow();
        roomsCatRepository.delete(roomsCat);

        return ResponseEntity.ok().build();
    }

}
