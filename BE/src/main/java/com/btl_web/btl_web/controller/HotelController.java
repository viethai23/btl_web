package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.Entity.Hotel;
import com.btl_web.btl_web.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
@CrossOrigin
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/")
    public List<Hotel> getAllHotel() {
        return hotelService.getAllHotel();
    }

}
