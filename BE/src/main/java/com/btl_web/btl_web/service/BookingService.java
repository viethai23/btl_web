package com.btl_web.btl_web.service;

import com.btl_web.btl_web.model.dto.BookingRequestDto;
import com.btl_web.btl_web.model.dto.BookingResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(BookingRequestDto dto);
    BookingResponseDto updateBooking(Long id, BookingRequestDto dto);
    void deleteBooking(Long id);
    BookingResponseDto getBookingById(Long id);
    List<BookingResponseDto> getAllBookings();
    List<BookingResponseDto> getAllBookingsByClientId(Long clientId);
    List<BookingResponseDto> getAllBookingsByRoomId(Long roomId);
    List<String> getRoomsBookedByClient(Long clientId);
    double getTotalPaymentByClient(Long clientId);
}

