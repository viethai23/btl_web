package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.RoomMapper;
import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.dto.RoomRequestDto;
import com.btl_web.btl_web.model.dto.RoomResponseDto;
import com.btl_web.btl_web.repository.BookingRepository;
import com.btl_web.btl_web.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

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
        room.setId(null);
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
    public boolean isRoomAvailable(Long roomId, String checkin, String checkout) {
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);
        for (Booking booking : bookings) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ci = LocalDate.parse(checkin, formatter);
            LocalDate co = LocalDate.parse(checkout, formatter);
            LocalDate bci = LocalDate.parse(booking.getCheckinDate(), formatter);
            LocalDate bco = LocalDate.parse(booking.getCheckoutDate(), formatter);
            if ((ci.isBefore(bci) && co.isAfter(bci) && co.isBefore(bco)) || // checkin < booking.ci < checkout < booking.cc
                    (ci.isAfter(bci) && ci.isBefore(bco) && co.isAfter(bco)) || // booking.ci < checkin < booking.co < checkout
                    (bci.isBefore(ci) && bco.isAfter(co)) || // booking.ci < checkin < checkout < booking.co
                    (ci.isBefore(bci) && bco.isAfter(bci) && co.isBefore(bco))) { // ci < booking.ci < booking.co < co
                return false;
            }
        }
        return true;
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}


