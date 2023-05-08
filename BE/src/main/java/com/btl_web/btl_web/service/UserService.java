package com.btl_web.btl_web.service;

import com.btl_web.btl_web.model.dto.UserRequestDto;
import com.btl_web.btl_web.model.dto.UserResponseDto;

public interface UserService {
    UserResponseDto addUser(UserRequestDto userRequestDto);
    UserResponseDto updateUserPassword(Long id, String newPassword);
    UserResponseDto getUserByUsernameAndPassword(String username, String password);
}
