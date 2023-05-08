package com.btl_web.btl_web.model.dto;
import lombok.*;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDto {
    private String roomName;
    private String roomType;
    private Integer roomSize;
    private Integer maxOccupancy;
    private BigDecimal price;
    private Long hotelId;
}
