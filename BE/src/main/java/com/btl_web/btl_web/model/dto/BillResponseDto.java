package com.btl_web.btl_web.model.dto;

import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.Client;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillResponseDto {
    private Long id;
    private String paymentDate;
    private String paymentMethod;
    private BookingResponseDto booking;
    private ClientResponseDto client;
}
