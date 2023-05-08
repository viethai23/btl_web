package com.btl_web.btl_web.model.dto;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDto {
    private Long id;
    private String name;
    private String address;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String amenities;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
