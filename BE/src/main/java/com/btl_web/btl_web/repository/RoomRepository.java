package com.btl_web.btl_web.repository;

import com.btl_web.btl_web.model.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotel_Id(Long hotelId);

    List<Room> findByRoomNameContainingIgnoreCase(String name);

    List<Room> findAllByOrderByPriceAsc();

    List<Room> findAllByOrderByPriceDesc();
}
