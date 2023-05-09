package com.btl_web.btl_web.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequestDto {
    private String name;
    private String address;
    @JsonProperty("opening_time")
    private String openingTime;
    @JsonProperty("closing_time")
    private String closingTime;
    private String amenities;
    private double rating;
}
