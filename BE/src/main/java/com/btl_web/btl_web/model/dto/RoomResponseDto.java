package com.btl_web.btl_web.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {
    private Long id;
    @JsonProperty("room_name")
    private String roomName;
    @JsonProperty("room_type")
    private String roomType;
    @JsonProperty("room_size")
    private Integer roomSize;
    @JsonProperty("max_occupancy")
    private Integer maxOccupancy;
    private double price;
    private Long hotelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
