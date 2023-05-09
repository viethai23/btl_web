package com.btl_web.btl_web.mapper;

import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.Entity.User;
import com.btl_web.btl_web.model.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {


    public BookingMapper() {
    }

    public Booking toEntity(BookingRequestDto dto) {
        Booking booking = new Booking();
        booking.setBookingDate(dto.getBookingDate());
        booking.setCheckinDate(dto.getCheckinDate());
        booking.setCheckoutDate(dto.getCheckoutDate());
        booking.setNumOfGuests(dto.getNumOfGuests());

        User user = new User();
        user.setId(dto.getUserId());
        booking.setUser(user);

        Room room = new Room();
        room.setId(dto.getRoomId());
        booking.setRoom(room);

        return booking;
    }

    public BookingResponseDto toDto(Booking entity) {
        BookingResponseDto dto = new BookingResponseDto();

        dto.setId(entity.getId());
        dto.setBookingDate(entity.getBookingDate());
        dto.setCheckinDate(entity.getCheckinDate());
        dto.setCheckoutDate(entity.getCheckoutDate());
        dto.setNumOfGuests(entity.getNumOfGuests());

        UserResponseDto UserDto = new UserResponseDto();
        UserDto.setId(entity.getUser().getId());
        UserDto.setAddress(entity.getUser().getAddress());
        UserDto.setEmail(entity.getUser().getEmail());
        UserDto.setFullName(entity.getUser().getFullName());
        UserDto.setPhoneNumber(entity.getUser().getPhoneNumber());
        dto.setUser(UserDto);

        RoomResponseDto roomDto = new RoomResponseDto();
        roomDto.setId(entity.getRoom().getId());
        roomDto.setRoomName(entity.getRoom().getRoomName());
        roomDto.setRoomSize(entity.getRoom().getRoomSize());
        roomDto.setRoomType(entity.getRoom().getRoomType());
        roomDto.setPrice(entity.getRoom().getPrice());
        roomDto.setMaxOccupancy(entity.getRoom().getMaxOccupancy());

        HotelResponseDto hotelDto = new HotelResponseDto();
        hotelDto.setId(entity.getRoom().getHotel().getId());
        hotelDto.setAddress(entity.getRoom().getHotel().getAddress());
        hotelDto.setName(entity.getRoom().getHotel().getName());
        hotelDto.setOpeningTime(entity.getRoom().getHotel().getOpeningTime());
        hotelDto.setClosingTime(entity.getRoom().getHotel().getClosingTime());
        hotelDto.setAmenities(entity.getRoom().getHotel().getAmenities());
        hotelDto.setRating(entity.getRoom().getHotel().getRating());
        roomDto.setHotel(hotelDto);

        dto.setRoom(roomDto);

        return dto;
    }

    public List<BookingResponseDto> toDtoList(List<Booking> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
