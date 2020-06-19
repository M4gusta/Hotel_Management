package com.magusta.HotelRestAPI.controllers;

import com.magusta.HotelRestAPI.models.Guest;
import com.magusta.HotelRestAPI.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GuestController{

    @Autowired
    GuestRepository guestRepository;

    @GetMapping("/guests")
    public List<Guest> findAll(){
        return guestRepository.findAll();
    }

    @GetMapping("/guests/{id}")
    public Guest findById(@PathVariable(value = "id") int guestId){
        return guestRepository.findById(guestId).orElseThrow();
    }

    @GetMapping("/guests/{fname}&{lname}")
    public List<Guest> findByFirstAndLastName(@PathVariable(value = "fname") String fName, @PathVariable(value = "lname") String lName){
        return guestRepository.findByFirstNameAndLastName(fName, lName);
    }

    @PostMapping("/guests")
    public Guest insertGuest(@Validated @RequestBody Guest guest){
        return guestRepository.save(guest);
    }

    @PutMapping("/guests/{id}")
    public Guest updateGuest(@PathVariable(value = "id") int guestId,@Validated @RequestBody Guest guest){
        Guest guest1 = guestRepository.findById(guestId).orElseThrow();
        guest.setGuestId(guest1.getGuestId());

        return guestRepository.save(guest);
    }

    @DeleteMapping("/guests/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable(value = "id") int guestId){
        Guest guest = guestRepository.findById(guestId).orElseThrow();

        guestRepository.delete(guest);

        return ResponseEntity.ok().build();
    }
}
