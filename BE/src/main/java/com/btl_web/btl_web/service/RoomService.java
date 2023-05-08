package com.btl_web.btl_web.service;

import com.btl_web.btl_web.model.dto.RoomRequestDto;
import com.btl_web.btl_web.model.dto.RoomResponseDto;

import java.util.List;

public interface RoomService {

    List<RoomResponseDto> getAllRooms();

    List<RoomResponseDto> getRoomsByHotelId(Long hotelId);

    List<RoomResponseDto> searchRoomsByName(String name);

    List<RoomResponseDto> getRoomsSortedByPrice(boolean ascending);

    RoomResponseDto getRoomById(Long id);

    RoomResponseDto createRoom(RoomRequestDto roomRequestDto);

    RoomResponseDto updateRoom(Long id, RoomRequestDto roomRequestDto);

    void deleteRoom(Long id);
}
