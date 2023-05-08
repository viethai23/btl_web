package com.btl_web.btl_web.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String username;
    private String password;
    private String identifier;
}
