package com.btl_web.btl_web.mapper;

import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.dto.RoomRequestDto;
import com.btl_web.btl_web.model.dto.RoomResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {
    // Chuyển từ request sang entity
    public Room toEntity(RoomRequestDto dto) {
        Room room = new Room();
        room.setName(dto.getName());
        room.setType(dto.getType());
        room.setPrice(dto.getPrice());
        room.setDescription(dto.getDescription());
        if (dto.getHotelId() != null) {
            Hotel hotel = new Hotel();
            hotel.setId(dto.getHotelId());
            room.setHotel(hotel);
        }
        return room;
    }

    // Chuyển từ entity sang response
    public RoomResponseDto toResponseDto(Room room) {
        RoomResponseDto dto = new RoomResponseDto();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setType(room.getType());
        dto.setPrice(room.getPrice());
        dto.setDescription(room.getDescription());
        if (room.getHotel() != null) {
            dto.setHotelId(room.getHotel().getId());
        }
        return dto;
    }
    // Chuyển từ entity sang list response
    public List<RoomResponseDto> toResponseDtoList(List<Room> rooms) {
        return rooms.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

}
