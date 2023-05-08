package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.dto.RoomRequestDto;
import com.btl_web.btl_web.model.dto.RoomResponseDto;
import com.btl_web.btl_web.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<RoomResponseDto> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping(params = "hotelId")
    public List<RoomResponseDto> getRoomsByHotelId(@RequestParam Long hotelId) {
        return roomService.getRoomsByHotelId(hotelId);
    }

    @GetMapping(params = "name")
    public List<RoomResponseDto> searchRoomsByName(@RequestParam String name) {
        return roomService.searchRoomsByName(name);
    }

    @GetMapping(params = "ascending")
    public List<RoomResponseDto> getRoomsSortedByPrice(@RequestParam boolean ascending) {
        return roomService.getRoomsSortedByPrice(ascending);
    }

    @GetMapping("/{id}")
    public RoomResponseDto getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@RequestBody RoomRequestDto roomRequestDto) {
        RoomResponseDto responseDto = roomService.createRoom(roomRequestDto);
        return ResponseEntity.created(URI.create("/api/rooms/" + responseDto.getId()))
                .body(responseDto);
    }

    @PutMapping("/{id}")
    public RoomResponseDto updateRoom(@PathVariable Long id, @RequestBody RoomRequestDto roomRequestDto) {
        return roomService.updateRoom(id, roomRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
