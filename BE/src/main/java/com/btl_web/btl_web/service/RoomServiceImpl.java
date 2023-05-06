package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.RoomMapper;
import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.repository.HotelRepository;
import com.btl_web.btl_web.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomMapper roomMapper;


    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getRoomsByType(String type) {
        return roomRepository.findAllByType(type);
    }

    public List<Room> getRoomsByPriceLessThanEqual(Float maxPrice) {
        return roomRepository.findByPriceLessThanEqual(maxPrice);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Room not found with id " + id));
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoom(Long id, Room roomDetails) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (!optionalRoom.isPresent()) {
            return null;
        }
        Room room = optionalRoom.get();
        room.setName(roomDetails.getName());
        room.setType(roomDetails.getType());
        room.setPrice(roomDetails.getPrice());
        room.setDescription(roomDetails.getDescription());
        if (roomDetails.getHotel() != null && roomDetails.getHotel().getId() != null) {
            Optional<Hotel> optionalHotel = hotelRepository.findById(roomDetails.getHotel().getId());
            if (optionalHotel.isPresent()) {
                room.setHotel(optionalHotel.get());
            }
        } else {
            room.setHotel(null);
        }
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        Room room = getRoomById(id);

        roomRepository.delete(room);
    }

}
