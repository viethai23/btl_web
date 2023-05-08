package com.btl_web.btl_web.mapper;

import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.model.dto.HotelRequestDto;
import com.btl_web.btl_web.model.dto.HotelResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    private ModelMapper modelMapper;

    public HotelMapper() {
        this.modelMapper = new ModelMapper();
    }

    public HotelResponseDto toDto(Hotel hotel) {
        return modelMapper.map(hotel, HotelResponseDto.class);
    }

    public Hotel toEntity(HotelRequestDto dto) {
        return modelMapper.map(dto, Hotel.class);
    }
}
