package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.Entity.Client;
import com.btl_web.btl_web.model.dto.BookingRequestDto;
import com.btl_web.btl_web.model.dto.BookingResponseDto;
import com.btl_web.btl_web.repository.ClientRepository;
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
    private final ClientRepository clientRepository;

    public BookingController(BookingService bookingService, ClientRepository clientRepository) {
        this.bookingService = bookingService;
        this.clientRepository = clientRepository;
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

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BookingResponseDto>> getAllBookingsByClientId(@PathVariable Long clientId) {
        List<BookingResponseDto> responseDtoList = bookingService.getAllBookingsByClientId(clientId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BookingResponseDto>> getAllBookingsByRoomId(@PathVariable Long roomId) {
        List<BookingResponseDto> responseDtoList = bookingService.getAllBookingsByRoomId(roomId);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}/booking-info")
    public ResponseEntity<Map<String, Object>> getClientBookingInfo(@PathVariable Long clientId) {
        Map<String, Object> response = new HashMap<>();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        response.put("id_client", client.getId());
        response.put("name_client", client.getFullName());
        response.put("rooms_booked", bookingService.getRoomsBookedByClient(clientId));
        response.put("total_payment", bookingService.getTotalPaymentByClient(clientId));
        return ResponseEntity.ok(response);
    }
}