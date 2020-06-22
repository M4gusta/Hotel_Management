package com.magusta.HotelRestAPI.controllers;

import com.magusta.HotelRestAPI.models.Room;
import com.magusta.HotelRestAPI.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {


    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/rooms")
    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id:[\\d]+}")
    public Room findById(@PathVariable(value = "id")int roomId){
        return roomRepository.findById(roomId).orElseThrow();
    }

    @GetMapping("/rooms/cat/{category}")
    public List<Room> findByCategory(@PathVariable(value = "category")String roomsCat){
        return roomRepository.findByroomCat(roomsCat);
    }

    @PostMapping("/rooms")
    public Room insertRoom(@RequestBody @Validated Room room){
        return roomRepository.save(room);
    }

    @PutMapping("/rooms/{id}")
    public Room updateRoom(@PathVariable(value = "id")int roomId, @Validated @RequestBody Room room){
        Room room1 = roomRepository.findById(roomId).orElseThrow();
        room.setRoomNum(room1.getRoomNum());

        return roomRepository.save(room);
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable(value = "id")int roomNum){
        Room room = roomRepository.findById(roomNum).orElseThrow();

        roomRepository.delete(room);

        return ResponseEntity.ok().build();
    }
}
