package com.btl_web.btl_web.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDto {
    private Long id;
    @JsonProperty("full_name")
    private String fullName;
    private String address;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String email;
}
