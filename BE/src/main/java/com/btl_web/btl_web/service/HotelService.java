package com.btl_web.btl_web.service;

import com.btl_web.btl_web.model.dto.HotelRequestDto;
import com.btl_web.btl_web.model.dto.HotelResponseDto;

import java.util.List;

public interface HotelService {

    HotelResponseDto create(HotelRequestDto requestDto);

    HotelResponseDto update(Long id, HotelRequestDto requestDto);

    void delete(Long id);

    List<HotelResponseDto> getAll();

    List<HotelResponseDto> getByName(String name);

    List<HotelResponseDto> getAllSortedByRating();
}
