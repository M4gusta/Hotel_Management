package com.magusta.HotelRestAPI.controllers;

import com.magusta.HotelRestAPI.models.Room;
import com.magusta.HotelRestAPI.repositories.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {


    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id:[\\d]+}")
    public Room getRoomById(@PathVariable(value = "id")int roomId){
        return roomRepository.findById(roomId).orElseThrow();
    }

    @GetMapping("/rooms/cat/{category}")
    public List<Room> getRoomsByCategory(@PathVariable(value = "category")String roomsCat){
        return roomRepository.findByroomCat(roomsCat);
    }

    @PostMapping("/rooms")
    public void insertRoom(@RequestBody @Valid Room room){
        roomRepository.save(room);
    }

    @PutMapping("/rooms/{id}")
    public void updateRoom(@PathVariable(value = "id")int roomId, @Valid @RequestBody Room room){
        Room room1 = roomRepository.findById(roomId).orElseThrow();
        room.setRoomNum(room1.getRoomNum());

        roomRepository.save(room);
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable(value = "id")int roomNum){
        Room room = roomRepository.findById(roomNum).orElseThrow();

        roomRepository.delete(room);

        return ResponseEntity.ok().build();
    }
}
