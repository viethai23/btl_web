package com.btl_web.btl_web.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    @JsonProperty("booking_date")
    private String bookingDate;
    @JsonProperty("checkin_date")
    private String checkinDate;
    @JsonProperty("checkout_date")
    private String checkoutDate;
    @JsonProperty("num_of_guests")
    private int numOfGuests;
    @JsonProperty("client_id")
    private Long clientId;
    @JsonProperty("room_id")
    private Long roomId;
}
