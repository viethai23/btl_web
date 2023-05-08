package com.btl_web.btl_web.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDto {
    private Long id;
    private String roomName;
    private String roomType;
    private Integer roomSize;
    private Integer maxOccupancy;
    private BigDecimal price;
    private Long hotelId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
