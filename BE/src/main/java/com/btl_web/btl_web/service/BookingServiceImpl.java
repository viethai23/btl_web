package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.BookingMapper;
import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.Client;
import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.dto.BookingRequestDto;
import com.btl_web.btl_web.model.dto.BookingResponseDto;
import com.btl_web.btl_web.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingResponseDto createBooking(BookingRequestDto dto) {
        Booking entity = bookingMapper.toEntity(dto);
        if (dto.getClientId() != null) {
            Client client = new Client();
            client.setId(dto.getClientId());
            entity.setClient(client);
        }
        if (dto.getRoomId() != null) {
            Room room = new Room();
            room.setId(dto.getRoomId());
            entity.setRoom(room);
        }
        return bookingMapper.toDto(bookingRepository.save(entity));
    }

    @Override
    public BookingResponseDto updateBooking(Long id, BookingRequestDto dto) {
        Booking entity = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        entity.setBookingDate(dto.getBookingDate());
        entity.setCheckinDate(dto.getCheckinDate());
        entity.setCheckoutDate(dto.getCheckoutDate());
        entity.setNumOfGuests(dto.getNumOfGuests());
        if (dto.getClientId() != null) {
            Client client = new Client();
            client.setId(dto.getClientId());
            entity.setClient(client);
        }
        if (dto.getRoomId() != null) {
            Room room = new Room();
            room.setId(dto.getRoomId());
            entity.setRoom(room);
        }
        return bookingMapper.toDto(bookingRepository.save(entity));
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public BookingResponseDto getBookingById(Long id) {
        Booking entity = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return bookingMapper.toDto(entity);
    }

    @Override
    public List<BookingResponseDto> getAllBookings() {
        List<Booking> entityList = bookingRepository.findAll();
        return bookingMapper.toDtoList(entityList);
    }

    @Override
    public List<BookingResponseDto> getAllBookingsByClientId(Long clientId) {
        List<Booking> entityList = bookingRepository.findByClientId(clientId);
        return bookingMapper.toDtoList(entityList);
    }

    @Override
    public List<BookingResponseDto> getAllBookingsByRoomId(Long roomId) {
        List<Booking> entityList = bookingRepository.findByRoomId(roomId);
        return bookingMapper.toDtoList(entityList);
    }
}
