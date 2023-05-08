package com.btl_web.btl_web.mapper;

import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.dto.BookingRequestDto;
import com.btl_web.btl_web.model.dto.BookingResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    private final ModelMapper modelMapper;

    public BookingMapper() {
        this.modelMapper = new ModelMapper();
    }

    public Booking toEntity(BookingRequestDto dto) {
        return modelMapper.map(dto, Booking.class);
    }

    public BookingResponseDto toDto(Booking entity) {
        return modelMapper.map(entity, BookingResponseDto.class);
    }

    public List<BookingResponseDto> toDtoList(List<Booking> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
