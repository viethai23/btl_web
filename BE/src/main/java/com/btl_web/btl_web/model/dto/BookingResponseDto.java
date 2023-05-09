package com.btl_web.btl_web.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private Long id;
    @JsonProperty("booking_date")
    private String bookingDate;
    @JsonProperty("checkin_date")
    private String checkinDate;
    @JsonProperty("checkout_date")
    private String checkoutDate;
    @JsonProperty("num_of_guests")
    private int numOfGuests;
    private UserResponseDto User;
    private RoomResponseDto room;
}
