package com.magusta.HotelRestAPI.repositories;

import com.magusta.HotelRestAPI.models.RoomsCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomsCatRepository extends JpaRepository <RoomsCat, Integer> {

}
