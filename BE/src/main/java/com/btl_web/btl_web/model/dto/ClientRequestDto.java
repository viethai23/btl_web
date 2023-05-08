package com.btl_web.btl_web.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDto {
    private String fullName;
    private String address;
    private String phoneNumber;
    private String email;
}
