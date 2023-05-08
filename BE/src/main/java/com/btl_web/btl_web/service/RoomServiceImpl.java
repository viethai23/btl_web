package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.RoomMapper;
import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.dto.RoomRequestDto;
import com.btl_web.btl_web.model.dto.RoomResponseDto;
import com.btl_web.btl_web.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

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
        room = roomRepository.save(room);
        return roomMapper.toDto(room);
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
            Hotel hotel = new Hotel();
            hotel.setId(roomRequestDto.getHotelId());
            room.setHotel(hotel);
        }
        room = roomRepository.save(room);
        return roomMapper.toDto(room);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}


