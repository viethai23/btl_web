package com.btl_web.btl_web.mapper;

import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.dto.RoomRequestDto;
import com.btl_web.btl_web.model.dto.RoomResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    private ModelMapper modelMapper;

    public RoomMapper() {
        this.modelMapper = new ModelMapper();
    }

    public RoomResponseDto toDto(Room room) {
        return modelMapper.map(room, RoomResponseDto.class);
    }

    public Room toEntity(RoomRequestDto roomRequestDto) {
        return modelMapper.map(roomRequestDto, Room.class);
    }
}
