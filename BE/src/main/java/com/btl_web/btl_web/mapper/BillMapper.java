package com.btl_web.btl_web.mapper;

import com.btl_web.btl_web.model.Entity.Bill;
import com.btl_web.btl_web.model.Entity.Booking;
import com.btl_web.btl_web.model.Entity.User;
import com.btl_web.btl_web.model.dto.*;
import org.springframework.stereotype.Component;

@Component
public class BillMapper {

    public BillMapper(){};

    public static Bill toEntity(BillRequestDto requestDto) {
        Bill bill = new Bill();
        bill.setPaymentDate(requestDto.getPaymentDate());
        bill.setPaymentMethod(requestDto.getPaymentMethod());
        Booking booking = new Booking();
        booking.setId(requestDto.getBookingId());
        bill.setBooking(booking);
        return bill;
    }

    public static BillResponseDto toDto(Bill bill) {
        BillResponseDto dto = new BillResponseDto();
        dto.setId(bill.getId());
        dto.setPaymentDate(bill.getPaymentDate());
        dto.setPaymentMethod(bill.getPaymentMethod());

        BookingResponseDto bookingDto = new BookingResponseDto();
        bookingDto.setId(bill.getBooking().getId());
        bookingDto.setBookingDate(bill.getBooking().getBookingDate());
        bookingDto.setCheckinDate(bill.getBooking().getCheckinDate());
        bookingDto.setCheckoutDate(bill.getBooking().getCheckoutDate());
        bookingDto.setNumOfGuests(bill.getBooking().getNumOfGuests());

        UserResponseDto userDto = new UserResponseDto();
        userDto.setId(bill.getBooking().getUser().getId());
        userDto.setAddress(bill.getBooking().getUser().getAddress());
        userDto.setEmail(bill.getBooking().getUser().getEmail());
        userDto.setFullName(bill.getBooking().getUser().getFullName());
        userDto.setPhoneNumber(bill.getBooking().getUser().getPhoneNumber());
        bookingDto.setUser(userDto);

        RoomResponseDto roomDto = new RoomResponseDto();
        roomDto.setId(bill.getBooking().getRoom().getId());
        roomDto.setRoomName(bill.getBooking().getRoom().getRoomName());
        roomDto.setRoomSize(bill.getBooking().getRoom().getRoomSize());
        roomDto.setRoomType(bill.getBooking().getRoom().getRoomType());
        roomDto.setPrice(bill.getBooking().getRoom().getPrice());
        roomDto.setMaxOccupancy(bill.getBooking().getRoom().getMaxOccupancy());

        HotelResponseDto hotelDto = new HotelResponseDto();
        hotelDto.setId(bill.getBooking().getRoom().getHotel().getId());
        hotelDto.setAddress(bill.getBooking().getRoom().getHotel().getAddress());
        hotelDto.setName(bill.getBooking().getRoom().getHotel().getName());
        hotelDto.setOpeningTime(bill.getBooking().getRoom().getHotel().getOpeningTime());
        hotelDto.setClosingTime(bill.getBooking().getRoom().getHotel().getClosingTime());
        hotelDto.setAmenities(bill.getBooking().getRoom().getHotel().getAmenities());
        hotelDto.setRating(bill.getBooking().getRoom().getHotel().getRating());
        roomDto.setHotel(hotelDto);

        bookingDto.setRoom(roomDto);

        dto.setBooking(bookingDto);
        return dto;
    }
}
