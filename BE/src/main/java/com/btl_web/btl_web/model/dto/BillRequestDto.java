package com.btl_web.btl_web.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequestDto {
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("booking_id")
    private Long bookingId;
}
