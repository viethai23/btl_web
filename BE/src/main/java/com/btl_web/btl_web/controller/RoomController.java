package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.dto.RoomRequestDto;
import com.btl_web.btl_web.model.dto.RoomResponseDto;
import com.btl_web.btl_web.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin
public class RoomController {

    @Autowired
    private RoomService roomService;
    // Lấy tất cả các phòng
    @GetMapping
    public List<RoomResponseDto> getAllRooms() {
        return roomService.getAllRooms();
    }
    // Lấy phòng theo id hotel
    @GetMapping(params = "hotelId")
    public List<RoomResponseDto> getRoomsByHotelId(@RequestParam Long hotelId) {
        return roomService.getRoomsByHotelId(hotelId);
    }
    // Lấy phòng theo tên
    @GetMapping(params = "name")
    public List<RoomResponseDto> searchRoomsByName(@RequestParam String name) {
        return roomService.searchRoomsByName(name);
    }
    // Lấy phòng sắp xếp
    @GetMapping(params = "ascending")
    public List<RoomResponseDto> getRoomsSortedByPrice(@RequestParam boolean ascending) {
        return roomService.getRoomsSortedByPrice(ascending);
    }
    // Lấy phòng theo id
    @GetMapping("/{id}")
    public RoomResponseDto getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }
    // Tạo phòng mới
    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@RequestBody RoomRequestDto roomRequestDto) {
        RoomResponseDto responseDto = roomService.createRoom(roomRequestDto);
        return ResponseEntity.created(URI.create("/api/rooms/" + responseDto.getId()))
                .body(responseDto);
    }
    // Sửa thông tin phòng
    @PutMapping("/{id}")
    public RoomResponseDto updateRoom(@PathVariable Long id, @RequestBody RoomRequestDto roomRequestDto) {
        return roomService.updateRoom(id, roomRequestDto);
    }
    // Xóa phòng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
    // Kiểm tra phòng đã được đặt chưa
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkRoomAvailability(@RequestParam Long roomId, @RequestParam String checkinDate, @RequestParam String checkoutDate, @RequestParam Integer numOfGuests) {
        Boolean isAvailable = roomService.isRoomAvailable(roomId, checkinDate, checkoutDate, numOfGuests);
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }
}
