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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

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
    public boolean isRoomAvailable(Long roomId, LocalDate checkin, LocalDate checkout) {
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);
        for (Booking booking : bookings) {
            if ((checkin.isBefore(booking.getCheckinDate()) && checkout.isAfter(booking.getCheckinDate()) && checkout.isBefore(booking.getCheckoutDate())) ||
                    (checkin.isAfter(booking.getCheckinDate()) && checkin.isBefore(booking.getCheckoutDate()) && checkout.isAfter(booking.getCheckoutDate())) ||
                    (booking.getCheckinDate().isBefore(checkin) && booking.getCheckoutDate().isAfter(checkout)) ||
                    (booking.getCheckinDate().isBefore(checkin) && booking.getCheckoutDate().isAfter(checkin) && booking.getCheckoutDate().isBefore(checkout))) {
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


