package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.BillMapper;
import com.btl_web.btl_web.model.Entity.Bill;
import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.Client;
import com.btl_web.btl_web.model.Entity.Room;
import com.btl_web.btl_web.model.dto.BillRequestDto;
import com.btl_web.btl_web.model.dto.BillResponseDto;
import com.btl_web.btl_web.repository.BillRepository;
import com.btl_web.btl_web.repository.BookingRepository;
import com.btl_web.btl_web.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillMapper billMapper;
    @Autowired
    private ClientRepository clientRepository;
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
        if (dto.getClientId() != null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + dto.getClientId()));;
            bill.setClient(client);
        }
        if (dto.getBookingId() != null) {
            Booking booking = bookingRepository.findById(dto.getBookingId())
                    .orElseThrow(() -> new RuntimeException("Booking not found with id: " + dto.getBookingId()));;
            bill.setBooking(booking);
        }
        return billMapper.toDto(billRepository.save(bill));
    }

    @Override
    public BillResponseDto updateBill(Long id, BillRequestDto dto) {
        Optional<Bill> optionalBill = billRepository.findById(id);
        if (optionalBill.isPresent()) {
            Bill bill = optionalBill.get();
            bill.setPaymentDate(dto.getPaymentDate());
            bill.setPaymentMethod(dto.getPaymentMethod());
            if (dto.getClientId() != null) {
                Client client = clientRepository.findById(dto.getClientId())
                        .orElseThrow(() -> new RuntimeException("Client not found with id: " + dto.getClientId()));;
                bill.setClient(client);
            }
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

}
