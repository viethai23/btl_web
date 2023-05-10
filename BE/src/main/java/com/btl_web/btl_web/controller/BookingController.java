package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.Entity.User;
import com.btl_web.btl_web.model.dto.BookingRequestDto;
import com.btl_web.btl_web.model.dto.BookingResponseDto;
import com.btl_web.btl_web.repository.UserRepository;
import com.btl_web.btl_web.service.BookingService;
import com.btl_web.btl_web.validation.Validation;
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
    private final Validation validation;

    public BookingController(BookingService bookingService, UserRepository userRepository, Validation validation) {
        this.bookingService = bookingService;
        this.userRepository = userRepository;
        this.validation = validation;
    }
    // Tạo booking mới
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDto dto) {
        List<String> list_error = validation.getInputError(dto);
        if (!list_error.isEmpty()){
            return new ResponseEntity<>(list_error, HttpStatus.BAD_REQUEST);
        }
        if(bookingService.isRoomAvailable(dto.getRoomId(), dto.getCheckinDate(), dto.getCheckoutDate() , dto.getNumOfGuests()) == false)
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        BookingResponseDto responseDto = bookingService.createBooking(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    // Sửa booking
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody BookingRequestDto dto) {
            List<String> list_error = validation.getInputError(dto);
            if (!list_error.isEmpty()){
                return new ResponseEntity<>(list_error, HttpStatus.BAD_REQUEST);
            }
            if(bookingService.isRoomAvailable(dto.getRoomId(), dto.getCheckinDate(), dto.getCheckoutDate() , dto.getNumOfGuests()) == false)
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            BookingResponseDto responseDto = bookingService.updateBooking(id, dto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    // Xóa booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
    // Lấy booking bằng id
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id) {
        BookingResponseDto responseDto = bookingService.getBookingById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    // Lấy tất cả booking
    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        List<BookingResponseDto> responseDtoList = bookingService.getAllBookings();
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }
    // Lấy booking theo user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDto>> getAllBookingsByUserId(@PathVariable Long userId) {
        List<BookingResponseDto> responseDtoList = bookingService.getAllBookingsByUserId(userId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }
    // Lấy booking theo room id
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BookingResponseDto>> getAllBookingsByRoomId(@PathVariable Long roomId) {
        List<BookingResponseDto> responseDtoList = bookingService.getAllBookingsByRoomId(roomId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }
    // Thống kê booking theo người dùng
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