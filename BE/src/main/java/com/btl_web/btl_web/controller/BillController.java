package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.dto.BillRequestDto;
import com.btl_web.btl_web.model.dto.BillResponseDto;
import com.btl_web.btl_web.model.dto.BookingResponseDto;
import com.btl_web.btl_web.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping
    public List<BillResponseDto> getAllBills() {
        return billService.getAllBills();
    }

    @GetMapping("/{id}")
    public BillResponseDto getBillById(@PathVariable("id") Long id) {
        return billService.getBillById(id);
    }

    @PostMapping
    public ResponseEntity<?> createBill(@RequestBody BillRequestDto requestDto) {
        if(billService.isBookingIdExist(requestDto.getBookingId()))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        BillResponseDto responseDto = billService.createBill(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public BillResponseDto updateBill(@PathVariable("id") Long id, @RequestBody BillRequestDto requestDto) {
        return billService.updateBill(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable("id") Long id) {
        billService.deleteBill(id);
    }

    @GetMapping("/{userId}/bills")
    public ResponseEntity<List<BillResponseDto>> getBillsByUserId(@PathVariable Long userId) {
        List<BillResponseDto> billResponseDtos = billService.getBillsByUserId(userId);
        return new ResponseEntity<>(billResponseDtos, HttpStatus.OK);
    }
    // Thống kê bill và tổng doanh thu theo ngày bắt đầu và ngày kết thúc
    @GetMapping("/date-range")
    public ResponseEntity<?> getBillsByDateRange(@RequestParam("startDay") String startDay,
                                                 @RequestParam("endDay") String endDay) {
        Map<String, Object> result = billService.getBillsByDateRange(startDay, endDay);
        return ResponseEntity.ok(result);
    }
    // Thống kê billvafaf tổng doanh thu theo user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBillsByUserIdWithTotalAmount(@PathVariable Long userId) {
        Map<String, Object> result = billService.getBillsByUserIdWithTotalAmount(userId);
        return ResponseEntity.ok(result);
    }
    // Thống kê billvafaf tổng doanh thu theo user và day
    @GetMapping("/bills/total-amount/{userId}/{dayStart}/{dayEnd}")
    public ResponseEntity<?> getTotalAmountByUserIdAndDate(
            @PathVariable Long userId,
            @PathVariable String dayStart,
            @PathVariable String dayEnd) {
        Map<String, Object> result = billService.getTotalAmountByUserIdAndDate(userId, dayStart, dayEnd);
        return ResponseEntity.ok(result);
    }

}
