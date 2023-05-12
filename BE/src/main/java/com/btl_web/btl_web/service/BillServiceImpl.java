package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.BillMapper;
import com.btl_web.btl_web.model.Entity.Bill;
import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.Entity.User;
import com.btl_web.btl_web.model.dto.BillRequestDto;
import com.btl_web.btl_web.model.dto.BillResponseDto;
import com.btl_web.btl_web.repository.BillRepository;
import com.btl_web.btl_web.repository.BookingRepository;
import com.btl_web.btl_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<BillResponseDto> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return bills.stream().map(BillMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BillResponseDto getBillById(Long id) {
        Optional<Bill> optionalBill = billRepository.findById(id);
        if (optionalBill.isPresent()) {
            Bill bill = optionalBill.get();
            return BillMapper.toDto(bill);
        } else {
            throw new RuntimeException("Bill not found with id " + id);
        }
    }

    @Override
    public BillResponseDto createBill(BillRequestDto dto) {
        Bill bill = billMapper.toEntity(dto);
        if (dto.getBookingId() != null) {
            Booking booking = bookingRepository.findById(dto.getBookingId())
                    .orElseThrow(() -> new RuntimeException("Booking not found with id: " + dto.getBookingId()));
            // Tính tổng tiền phải thanh toán = giá phòng * số ngày đặt
            Room room = booking.getRoom();
            System.out.println(room.getPrice());
            double price = room.getPrice();
            double amountTotal = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date1 = sdf.parse(booking.getCheckinDate());
                Date date2 = sdf.parse(booking.getCheckoutDate());
                long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                System.out.println(diffInDays);
                amountTotal = price * diffInDays;

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            bill.setAmountTotal(amountTotal);
            bill.setBooking(booking);
        }
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String time_now = dateTime.format(formatter);
        bill.setPaymentDate(time_now);
        return billMapper.toDto(billRepository.save(bill));
    }

    @Override
    public BillResponseDto updateBill(Long id, BillRequestDto dto) {
        Optional<Bill> optionalBill = billRepository.findById(id);
        if (optionalBill.isPresent()) {
            Bill bill = optionalBill.get();
            bill.setPaymentMethod(dto.getPaymentMethod());
            if (dto.getBookingId() != null) {
                Booking booking = bookingRepository.findById(dto.getBookingId())
                        .orElseThrow(() -> new RuntimeException("Booking not found with id: " + dto.getBookingId()));;
                bill.setBooking(booking);
            }
            bill = billRepository.save(bill);
            return billMapper.toDto(billRepository.save(bill));
        } else {
            throw new RuntimeException("Bill not found with id " + id);
        }
    }

    @Override
    public void deleteBill(Long id) {
        Optional<Bill> optionalBill = billRepository.findById(id);
        if (optionalBill.isPresent()) {
            billRepository.deleteById(id);
        } else {
            throw new RuntimeException("Bill not found with id " + id);
        }
    }

    @Override
    public List<BillResponseDto> getBillsByUserId(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        List<BillResponseDto> billResponseDtos = new ArrayList<>();
        for (Booking booking : bookings) {
            List<Bill> bills = billRepository.findByBookingId(booking.getId());
            for (Bill bill : bills) {
                billResponseDtos.add(billMapper.toDto(bill));
            }
        }
        return billResponseDtos;
    }

    @Override
    public boolean isBookingIdExist(Long bookingId) {
        return billRepository.existsByBookingId(bookingId);
    }

    @Override
    public Map<String, Object> getBillsByDateRange(String startDay, String endDay) {
        List<Bill> bills = billRepository.findByPaymentDateBetween(startDay, endDay);
        double totalAmount = 0;
        List<BillResponseDto> billResponseDtos = new ArrayList<>();
        for (Bill bill : bills) {
            BillResponseDto billResponseDto = billMapper.toDto(bill);
            billResponseDtos.add(billResponseDto);
            totalAmount += bill.getAmountTotal();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("bills", billResponseDtos);
        result.put("totalAmount", totalAmount);
        return result;
    }

    @Override
    public Map<String, Object> getBillsByUserIdWithTotalAmount(Long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        List<BillResponseDto> billResponseDtos = new ArrayList<>();
        double totalAmount = 0;
        for (Booking booking : bookings) {
            List<Bill> bills = billRepository.findByBookingId(booking.getId());
            for (Bill bill : bills) {
                billResponseDtos.add(billMapper.toDto(bill));
                totalAmount += bill.getAmountTotal();
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("bills", billResponseDtos);
        result.put("totalAmount", totalAmount);
        return result;
    }

    @Override
    public Map<String, Object> getTotalAmountByUserIdAndDate(Long userId, String dayStart, String dayEnd) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);
        double totalAmount = 0.0;
        List<BillResponseDto> billResponseDtos = new ArrayList<>();
        for (Booking booking : bookings) {
            List<Bill> bills = billRepository.findByBookingIdAndPaymentDateBetween(booking.getId(), dayStart, dayEnd);
            for (Bill bill : bills) {
                billResponseDtos.add(billMapper.toDto(bill));
                totalAmount += bill.getAmountTotal();
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("bills", billResponseDtos);
        result.put("totalAmount", totalAmount);
        return result;
    }


}
