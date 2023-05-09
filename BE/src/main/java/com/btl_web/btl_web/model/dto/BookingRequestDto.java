package com.btl_web.btl_web.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    @JsonProperty("checkin_date")
    private String checkinDate;
    @JsonProperty("checkout_date")
    private String checkoutDate;
    @JsonProperty("num_of_guests")
    private int numOfGuests;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("room_id")
    private Long roomId;
}
