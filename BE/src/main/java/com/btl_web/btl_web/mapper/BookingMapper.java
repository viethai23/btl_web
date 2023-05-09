package com.btl_web.btl_web.mapper;

import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.Client;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.dto.BookingRequestDto;
import com.btl_web.btl_web.model.dto.BookingResponseDto;
import com.btl_web.btl_web.model.dto.ClientResponseDto;
import com.btl_web.btl_web.model.dto.RoomResponseDto;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.NamingConventions;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    private final ModelMapper modelMapper;

    public BookingMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Booking toEntity(BookingRequestDto dto) {
        Booking booking = new Booking();
        booking.setBookingDate(dto.getBookingDate());
        booking.setCheckinDate(dto.getCheckinDate());
        booking.setCheckoutDate(dto.getCheckoutDate());
        booking.setNumOfGuests(dto.getNumOfGuests());

        Client client = new Client();
        client.setId(dto.getClientId());
        booking.setClient(client);

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

        ClientResponseDto clientDto = new ClientResponseDto();
        clientDto.setId(entity.getClient().getId());
        clientDto.setAddress(entity.getClient().getAddress());
        clientDto.setEmail(entity.getClient().getEmail());
        clientDto.setFullName(entity.getClient().getFullName());
        clientDto.setPhoneNumber(entity.getClient().getPhoneNumber());
        dto.setClient(clientDto);

        RoomResponseDto roomDto = new RoomResponseDto();
        roomDto.setId(entity.getRoom().getId());
        roomDto.setRoomName(entity.getRoom().getRoomName());
        roomDto.setRoomSize(entity.getRoom().getRoomSize());
        roomDto.setRoomType(entity.getRoom().getRoomType());
        roomDto.setPrice(entity.getRoom().getPrice());
        roomDto.setMaxOccupancy(entity.getRoom().getMaxOccupancy());
        dto.setRoom(roomDto);

        return dto;
    }

    public List<BookingResponseDto> toDtoList(List<Booking> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
