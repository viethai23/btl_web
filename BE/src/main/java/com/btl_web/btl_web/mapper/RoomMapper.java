package com.btl_web.btl_web.mapper;

import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.Client;
import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {


    public RoomMapper() {
    }

    public Room toEntity(RoomRequestDto dto) {
        Room room = new Room();
        room.setRoomName(dto.getRoomName());
        room.setRoomSize(dto.getRoomSize());
        room.setRoomType(dto.getRoomType());
        room.setPrice(dto.getPrice());
        room.setMaxOccupancy(dto.getMaxOccupancy());

        Hotel hotel = new Hotel();
        hotel.setId(dto.getHotelId());
        room.setHotel(hotel);

        return room;
    }

    public RoomResponseDto toDto(Room entity) {
        RoomResponseDto dto = new RoomResponseDto();

        dto.setId(entity.getId());
        dto.setRoomSize(entity.getRoomSize());
        dto.setRoomName(entity.getRoomName());
        dto.setRoomType(entity.getRoomType());
        dto.setPrice(entity.getPrice());
        dto.setMaxOccupancy(entity.getMaxOccupancy());

        HotelResponseDto hotelDto = new HotelResponseDto();
        hotelDto.setId(entity.getHotel().getId());
        hotelDto.setAddress(entity.getHotel().getAddress());
        hotelDto.setName(entity.getHotel().getName());
        hotelDto.setOpeningTime(entity.getHotel().getOpeningTime());
        hotelDto.setClosingTime(entity.getHotel().getClosingTime());
        hotelDto.setAmenities(entity.getHotel().getAmenities());
        hotelDto.setRating(entity.getHotel().getRating());
        dto.setHotel(hotelDto);

        return dto;
    }

}
