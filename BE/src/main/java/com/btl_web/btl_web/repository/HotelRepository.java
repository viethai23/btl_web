package com.btl_web.btl_web.repository;

import com.btl_web.btl_web.model.Entity.Hotel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByNameContaining(String name);

    List<Hotel> findAllByOrderByRatingDesc();
}
