package com.magusta.HotelRestAPI.repositories;

import com.magusta.HotelRestAPI.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository <Room, Integer> {

    List<Room> findByroomCat(String category);
}
