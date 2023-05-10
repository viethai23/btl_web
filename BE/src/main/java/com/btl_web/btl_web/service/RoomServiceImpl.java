package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.RoomMapper;
import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.dto.RoomRequestDto;
import com.btl_web.btl_web.model.dto.RoomResponseDto;
import com.btl_web.btl_web.repository.BookingRepository;
import com.btl_web.btl_web.repository.HotelRepository;
import com.btl_web.btl_web.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<RoomResponseDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(room -> roomMapper.toDto(room)).collect(Collectors.toList());
    }

    @Override
    public List<RoomResponseDto> getRoomsByHotelId(Long hotelId) {
        List<Room> rooms = roomRepository.findByHotel_Id(hotelId);
        return rooms.stream().map(room -> roomMapper.toDto(room)).collect(Collectors.toList());
    }

    @Override
    public List<RoomResponseDto> searchRoomsByName(String name) {
        List<Room> rooms = roomRepository.findByRoomNameContainingIgnoreCase(name);
        return rooms.stream().map(room -> roomMapper.toDto(room)).collect(Collectors.toList());
    }

    @Override
    public List<RoomResponseDto> getRoomsSortedByPrice(boolean ascending) {
        List<Room> rooms;
        if (ascending) {
            rooms = roomRepository.findAllByOrderByPriceAsc();
        } else {
            rooms = roomRepository.findAllByOrderByPriceDesc();
        }
        return rooms.stream().map(room -> roomMapper.toDto(room)).collect(Collectors.toList());
    }

    @Override
    public RoomResponseDto getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        return roomMapper.toDto(room);
    }

    @Override
    public RoomResponseDto createRoom(RoomRequestDto roomRequestDto) {
        Room room = roomMapper.toEntity(roomRequestDto);
        if (roomRequestDto.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(roomRequestDto.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomRequestDto.getHotelId()));
            room.setHotel(hotel);
        }
        return roomMapper.toDto(roomRepository.save(room));
    }

    @Override
    public RoomResponseDto updateRoom(Long id, RoomRequestDto roomRequestDto) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        room.setRoomName(roomRequestDto.getRoomName());
        room.setRoomType(roomRequestDto.getRoomType());
        room.setRoomSize(roomRequestDto.getRoomSize());
        room.setMaxOccupancy(roomRequestDto.getMaxOccupancy());
        room.setPrice(roomRequestDto.getPrice());
        if (roomRequestDto.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(roomRequestDto.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + roomRequestDto.getHotelId()));
            room.setHotel(hotel);
        }
        return roomMapper.toDto(roomRepository.save(room));
    }


    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}


