package com.btl_web.btl_web.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDto {
    @JsonProperty("room_name")
    private String roomName;
    @JsonProperty("room_type")
    private String roomType;
    @JsonProperty("room_size")
    private Integer roomSize;
    @JsonProperty("max_occupancy")
    private Integer maxOccupancy;
    private double price;
    @JsonProperty("hotel_id")
    private Long hotelId;
}
