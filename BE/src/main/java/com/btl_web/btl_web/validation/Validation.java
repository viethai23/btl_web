package com.btl_web.btl_web.validation;
import com.btl_web.btl_web.model.dto.BookingRequestDto;
import com.btl_web.btl_web.model.dto.HotelRequestDto;
import com.btl_web.btl_web.model.dto.RoomRequestDto;
import com.btl_web.btl_web.model.dto.UserRequestDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
@Component
public class Validation {


    public List<String> getInputError(UserRequestDto requestDto) {
        List<String> list = new ArrayList<>();
        if (requestDto.getPhoneNumber() == null) list.add("số điện thoại bị trống");
        if (requestDto.getUsername() == null) list.add("tên đăng nhập bị trống");
        if (requestDto.getPassword() == null) list.add("mật khẩu bị trống");
        if (requestDto.getFullName() == null) list.add("họ và tên bị trống");
        if (requestDto.getAddress() == null) list.add("địa chỉ bị trống");
        if (requestDto.getEmail() == null) list.add("email bị trống");
        else {
            String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(requestDto.getEmail());
            if (!matcher.matches()) {
                list.add("địa chỉ email không hợp lệ");
            }
        }
        return list;
    }

    public List<String> getInputError(RoomRequestDto requestDto) {
        List<String> list = new ArrayList<>();
        if (requestDto.getRoomName() == null) list.add("tên phòng bị trống");
        if (requestDto.getRoomType() == null) list.add("loại phòng bị trống");
        if (requestDto.getRoomSize() == null || requestDto.getRoomSize() <= 0) list.add("diện tích phòng không hợp lệ");
        if (requestDto.getMaxOccupancy() == null || requestDto.getMaxOccupancy() <= 0) list.add("số lượng khách tối đa không hợp lệ");
        if (requestDto.getPrice() <= 0) list.add("giá phòng không hợp lệ");
        return list;
    }

    public List<String> getInputError(HotelRequestDto requestDto) {
        List<String> list = new ArrayList<>();
        if (requestDto.getName() == null) list.add("tên khách sạn bị trống");
        if (requestDto.getAddress() == null) list.add("địa chỉ khách sạn bị trống");
        if (requestDto.getOpeningTime() == null || requestDto.getOpeningTime().isEmpty()) list.add("thời gian mở cửa không hợp lệ");
        if (requestDto.getClosingTime() == null || requestDto.getClosingTime().isEmpty()) list.add("thời gian đóng cửa không hợp lệ");
        if (requestDto.getAmenities() == null || requestDto.getAmenities().isEmpty()) list.add("tiện nghi không được để trống");
        if (requestDto.getRating() < 0 || requestDto.getRating() > 5) list.add("điểm đánh giá không hợp lệ");
        return list;
    }

    public List<String> getInputError(BookingRequestDto requestDto) {
        List<String> list = new ArrayList<>();
        if (requestDto.getCheckinDate() == null || requestDto.getCheckinDate().isEmpty()) list.add("ngày check-in không hợp lệ");
        if (requestDto.getCheckoutDate() == null || requestDto.getCheckoutDate().isEmpty()) list.add("ngày check-out không hợp lệ");
        if (requestDto.getNumOfGuests() <= 0) list.add("số lượng khách không hợp lệ");
        return list;
    }


}
