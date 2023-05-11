package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.BookingMapper;
import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.Entity.User;
import com.btl_web.btl_web.model.dto.BookingRequestDto;
import com.btl_web.btl_web.model.dto.BookingResponseDto;
import com.btl_web.btl_web.repository.BookingRepository;
import com.btl_web.btl_web.repository.HotelRepository;
import com.btl_web.btl_web.repository.RoomRepository;
import com.btl_web.btl_web.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingMapper bookingMapper, RoomRepository roomRepository, UserRepository userRepository, HotelRepository hotelRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public BookingResponseDto createBooking(BookingRequestDto dto) {
        Booking entity = bookingMapper.toEntity(dto);
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            entity.setUser(user);
        }
        if (dto.getRoomId() != null) {
            Room room = roomRepository.findById(dto.getRoomId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            entity.setRoom(room);
        }
        // Lấy thời gian hiện tại là thời gian booking lúc tạo mới 1 booking
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String time_now = dateTime.format(formatter);
        entity.setBookingDate(time_now);

        return bookingMapper.toDto(bookingRepository.save(entity));
    }

    @Override
    public BookingResponseDto updateBooking(Long id, BookingRequestDto dto) {
        Booking entity = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        entity.setCheckinDate(dto.getCheckinDate());
        entity.setCheckoutDate(dto.getCheckoutDate());
        entity.setNumOfGuests(dto.getNumOfGuests());
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            entity.setUser(user);
        }
        if (dto.getRoomId() != null) {
            Room room = roomRepository.findById(dto.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Room not found with id: " + dto.getUserId()));
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
    public List<BookingResponseDto> getAllBookingsByUserId(Long UserId) {
        List<Booking> entityList = bookingRepository.findByUserId(UserId);
        return bookingMapper.toDtoList(entityList);
    }

    @Override
    public List<BookingResponseDto> getAllBookingsByRoomId(Long roomId) {
        List<Booking> entityList = bookingRepository.findByRoomId(roomId);
        return bookingMapper.toDtoList(entityList);
    }

    @Override
    public List<String> getRoomsBookedByUser(Long UserId) {
        List<Booking> bookings = bookingRepository.findByUserId(UserId);
        List<String> result = new ArrayList<>();
        for (Booking booking : bookings) {
            String roomName = booking.getRoom().getRoomName();
            String hotelName = booking.getRoom().getHotel().getName();
            result.add(roomName + " - " + hotelName);
        }
        return result;
    }

    @Override
    public double getTotalPaymentByUser(Long UserId) {
        List<Booking> bookings = bookingRepository.findByUserId(UserId);
        double totalPayment = 0.0;
        for (Booking booking : bookings) {
            totalPayment += booking.getRoom().getPrice();
        }
        return totalPayment;
    }

    @Override
    public boolean isRoomAvailable(Long roomId, String checkin, String checkout, Integer numOfGuests) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
        if(numOfGuests > room.getMaxOccupancy())
            return false;
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);
        for (Booking booking : bookings) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ci = LocalDate.parse(checkin, formatter);
            LocalDate co = LocalDate.parse(checkout, formatter);
            LocalDate bci = LocalDate.parse(booking.getCheckinDate(), formatter);
            LocalDate bco = LocalDate.parse(booking.getCheckoutDate(), formatter);
            if(ci.isBefore(co)) {
                if(ci.isAfter(bco) || co.isBefore(bci))
                    continue;
            }
            return false;
        }
        return true;
    }
}
