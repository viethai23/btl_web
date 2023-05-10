package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.dto.BillRequestDto;
import com.btl_web.btl_web.model.dto.BillResponseDto;
import com.btl_web.btl_web.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public BillResponseDto createBill(@RequestBody BillRequestDto requestDto) {
        return billService.createBill(requestDto);
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

}
