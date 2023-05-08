package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.HotelMapper;
import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.model.dto.HotelRequestDto;
import com.btl_web.btl_web.model.dto.HotelResponseDto;
import com.btl_web.btl_web.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelServiceImpl(HotelRepository hotelRepository, HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

    @Override
    public HotelResponseDto create(HotelRequestDto requestDto) {
        Hotel entity = hotelMapper.toEntity(requestDto);
        entity = hotelRepository.save(entity);
        return hotelMapper.toDto(entity);
    }

    @Override
    public HotelResponseDto update(Long id, HotelRequestDto requestDto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        hotel.setName(requestDto.getName());
        hotel.setAddress(requestDto.getAddress());
        hotel.setOpeningTime(requestDto.getOpeningTime());
        hotel.setClosingTime(requestDto.getClosingTime());
        hotel.setAmenities(requestDto.getAmenities());
        hotel.setRating(requestDto.getRating());
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }

    @Override
    public void delete(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public List<HotelResponseDto> getAll() {
        List<Hotel> entities = hotelRepository.findAll();
        return entities.stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelResponseDto> getByName(String name) {
        List<Hotel> entities = hotelRepository.findByNameContaining(name);
        return entities.stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelResponseDto> getAllSortedByRating() {
        List<Hotel> entities = hotelRepository.findAllByOrderByRatingDesc();
        return entities.stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
    }
}
