package com.btl_web.btl_web.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillResponseDto {
    private Long id;
    @JsonProperty("payment_date")
    private String paymentDate;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("amount_total")
    private double amountTotal;
    private BookingResponseDto booking;
}
