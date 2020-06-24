package com.magusta.HotelRestAPI.controllers;

import com.magusta.HotelRestAPI.models.Guest;
import com.magusta.HotelRestAPI.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GuestController{

    private final GuestRepository guestRepository;

    @Autowired
    public GuestController(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @GetMapping("/guests")
    public List<Guest> getAllGuests(){
        return guestRepository.findAll();
    }

    @GetMapping("/guests/{id}")
    public Guest getGuestById(@PathVariable(value = "id") int guestId){
        return guestRepository.findById(guestId).orElseThrow();
    }

    @GetMapping("/guests/?fname={fname}&lname={lname}")
    public List<Guest> getGuestByFirstAndLastName(@PathVariable(value = "fname") String fName, @PathVariable(value = "lname") String lName){
        return guestRepository.findByFirstNameAndLastName(fName, lName);
    }

    @PostMapping("/guests")
    public void insertGuest(@Validated @RequestBody Guest guest){
        guestRepository.save(guest);
    }

    @PutMapping("/guests/{id}")
    public void updateGuest(@PathVariable(value = "id") int guestId, @Valid @RequestBody Guest guest){
        //Guest guest1 = guestRepository.findById(guestId).orElseThrow();
        guest.setGuestId(guestId);

        guestRepository.save(guest);
    }

    @DeleteMapping("/guests/{id}")
    public ResponseEntity<?> deleteGuest(@PathVariable(value = "id") int guestId){
        Guest guest = guestRepository.findById(guestId).orElseThrow();

        guestRepository.delete(guest);

        return ResponseEntity.ok().build();
    }
}
