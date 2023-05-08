package com.btl_web.btl_web.model.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    private LocalDate bookingDate;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Integer numOfGuests;
    private Long clientId;
    private Long roomId;
}
