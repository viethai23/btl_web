package com.btl_web.btl_web.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private Long id;
    private LocalDate bookingDate;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Integer numOfGuests;
    private ClientResponseDto client;
    private RoomResponseDto room;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
