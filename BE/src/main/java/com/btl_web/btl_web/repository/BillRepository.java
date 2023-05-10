package com.btl_web.btl_web.repository;

import com.btl_web.btl_web.model.Entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByBookingId(Long bookingId);
}
