package com.btl_web.btl_web.service;

import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HotelServiceImpl implements HotelService{
    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public List<Hotel> getAllHotel() {
        return hotelRepository.findAll();
    }
}
