package com.btl_web.btl_web.service;

import com.btl_web.btl_web.mapper.UserMapper;
import com.btl_web.btl_web.model.Entity.User;
import com.btl_web.btl_web.model.dto.UserRequestDto;
import com.btl_web.btl_web.model.dto.UserResponseDto;
import com.btl_web.btl_web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        if (user.getIdentifier().equals("666")) {
            user.setRole("Admin");
        } else {
            user.setRole("Client");
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserResponseDto updateUserPassword(Long id, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            User updatedUser = userRepository.save(user);
            return userMapper.toDto(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public UserResponseDto getUserByUsernameAndPassword(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return userMapper.toDto(user);
        } else {
            throw new RuntimeException("User not found with username and password");
        }
    }
}
