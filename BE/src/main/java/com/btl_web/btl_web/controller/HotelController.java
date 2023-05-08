package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.dto.HotelRequestDto;
import com.btl_web.btl_web.model.dto.HotelResponseDto;
import com.btl_web.btl_web.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    //Tạo khách sạn mới
    @PostMapping
    public ResponseEntity<HotelResponseDto> create(@RequestBody HotelRequestDto requestDto) {
        HotelResponseDto responseDto = hotelService.create(requestDto);
        return ResponseEntity.created(URI.create("/api/hotels/" + responseDto.getId()))
                .body(responseDto);
    }
    //Update thông tin khách sạn
    @PutMapping("/{id}")
    public ResponseEntity<HotelResponseDto> update(@PathVariable Long id, @RequestBody HotelRequestDto requestDto) {
        HotelResponseDto responseDto = hotelService.update(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }
    //Xóa khách sạn
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
    //Lấy tất cả khách sạn
    @GetMapping
    public ResponseEntity<List<HotelResponseDto>> getAll() {
        List<HotelResponseDto> responseDtos = hotelService.getAll();
        return ResponseEntity.ok(responseDtos);
    }
    //Tìm khách sạn theo tên
    @GetMapping("/search")
    public ResponseEntity<List<HotelResponseDto>> getByName(@RequestParam String name) {
        List<HotelResponseDto> responseDtos = hotelService.getByName(name);
        return ResponseEntity.ok(responseDtos);
    }
    //Sắp xếp khách sạn theo rating
    @GetMapping("/rating")
    public ResponseEntity<List<HotelResponseDto>> getAllSortedByRating() {
        List<HotelResponseDto> responseDtos = hotelService.getAllSortedByRating();
        return ResponseEntity.ok(responseDtos);
    }
}
