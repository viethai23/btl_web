package com.btl_web.btl_web.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String username;
    private String password;
    private String identifier;
    @JsonProperty("full_name")
    private String fullName;
    private String address;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String email;
}
