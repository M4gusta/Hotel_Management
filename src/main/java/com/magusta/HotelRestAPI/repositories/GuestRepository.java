package com.magusta.HotelRestAPI.repositories;

import com.magusta.HotelRestAPI.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository <Guest, Integer> {

    List<Guest> findByFirstNameAndLastName(String fName, String lName);

}
