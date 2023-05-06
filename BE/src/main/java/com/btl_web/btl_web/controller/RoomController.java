package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.mapper.RoomMapper;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.dto.RoomRequestDto;
import com.btl_web.btl_web.model.dto.RoomResponseDto;
import com.btl_web.btl_web.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMapper roomMapper;

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponseDto> dtos = roomMapper.toResponseDtoList(rooms);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(params = "type")
    public ResponseEntity<List<RoomResponseDto>> getRoomsByType(@RequestParam("type") String type) {
        List<Room> rooms = roomService.getRoomsByType(type);
        List<RoomResponseDto> dtos = roomMapper.toResponseDtoList(rooms);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(params = "maxPrice")
    public ResponseEntity<List<RoomResponseDto>> getRoomsByPriceLessThanEqual(@RequestParam("maxPrice") Float maxPrice) {
        List<Room> rooms = roomService.getRoomsByPriceLessThanEqual(maxPrice);
        List<RoomResponseDto> dtos = roomMapper.toResponseDtoList(rooms);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDto> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        RoomResponseDto dto = roomMapper.toResponseDto(room);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RoomResponseDto> createRoom(@RequestBody RoomRequestDto roomRequestDto) {
        Room room = roomMapper.toEntity(roomRequestDto);
        Room createdRoom = roomService.createRoom(room);
        RoomResponseDto createdDto = roomMapper.toResponseDto(createdRoom);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDto> updateRoom(@PathVariable Long id, @RequestBody RoomRequestDto roomRequestDto) {
        Room room = roomMapper.toEntity(roomRequestDto);
        Room updatedRoom = roomService.updateRoom(id, room);
        if (updatedRoom == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        RoomResponseDto updatedDto = roomMapper.toResponseDto(updatedRoom);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}