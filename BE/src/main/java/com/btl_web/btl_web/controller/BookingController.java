package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.Entity.User;
import com.btl_web.btl_web.model.dto.BookingRequestDto;
import com.btl_web.btl_web.model.dto.BookingResponseDto;
import com.btl_web.btl_web.repository.UserRepository;
import com.btl_web.btl_web.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
public class BookingController {

    private final BookingService bookingService;
    private final UserRepository userRepository;

    public BookingController(BookingService bookingService, UserRepository userRepository) {
        this.bookingService = bookingService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto dto) {
        BookingResponseDto responseDto = bookingService.createBooking(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponseDto> updateBooking(@PathVariable Long id, @RequestBody BookingRequestDto dto) {
            BookingResponseDto responseDto = bookingService.updateBooking(id, dto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id) {
        BookingResponseDto responseDto = bookingService.getBookingById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        List<BookingResponseDto> responseDtoList = bookingService.getAllBookings();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDto>> getAllBookingsByUserId(@PathVariable Long userId) {
        List<BookingResponseDto> responseDtoList = bookingService.getAllBookingsByUserId(userId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BookingResponseDto>> getAllBookingsByRoomId(@PathVariable Long roomId) {
        List<BookingResponseDto> responseDtoList = bookingService.getAllBookingsByRoomId(roomId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/booking-info")
    public ResponseEntity<Map<String, Object>> getUserBookingInfo(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        response.put("id_user", user.getId());
        response.put("name_user", user.getFullName());
        response.put("rooms_booked", bookingService.getRoomsBookedByUser(userId));
        response.put("total_payment", bookingService.getTotalPaymentByUser(userId));
        return ResponseEntity.ok(response);
    }
}