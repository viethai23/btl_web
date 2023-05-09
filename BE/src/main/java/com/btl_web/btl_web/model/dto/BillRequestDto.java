package com.btl_web.btl_web.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequestDto {
    private String paymentDate;
    private String paymentMethod;
    private Long bookingId;
    private Long UserId;
}
