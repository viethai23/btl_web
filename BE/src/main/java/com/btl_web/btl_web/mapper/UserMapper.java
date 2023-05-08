package com.btl_web.btl_web.mapper;

import com.btl_web.btl_web.model.Entity.User;
import com.btl_web.btl_web.model.dto.UserRequestDto;
import com.btl_web.btl_web.model.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;


    public UserMapper() {
        this.modelMapper = new ModelMapper();
    }

    public User toEntity(UserRequestDto userRequestDto) {
        return modelMapper.map(userRequestDto, User.class);
    }

    public UserResponseDto toDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }
}
