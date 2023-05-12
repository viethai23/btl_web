package com.btl_web.btl_web.service;

import com.btl_web.btl_web.model.dto.BillRequestDto;
import com.btl_web.btl_web.model.dto.BillResponseDto;

import java.util.List;
import java.util.Map;

public interface BillService {
    public List<BillResponseDto> getAllBills();
    public BillResponseDto getBillById(Long id);
    public BillResponseDto createBill(BillRequestDto requestDto);
    public BillResponseDto updateBill(Long id, BillRequestDto requestDto);
    public void deleteBill(Long id);
//    public Double calculateTotalPaymentByUser(Long UserId);
    public List<BillResponseDto> getBillsByUserId(Long userId);
    boolean isBookingIdExist(Long bookingId);
    public Map<String, Object> getBillsByDateRange(String startDay, String endDay);
    public Map<String, Object> getBillsByUserIdWithTotalAmount(Long userId);
}
