package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.dto.UserRequestDto;
import com.btl_web.btl_web.model.dto.UserResponseDto;
import com.btl_web.btl_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    //Thêm 1 user (dùng cho chức năng đăng ký tài khoản)
    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.addUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }
    //Thay đổi mật khẩu (dùng cho chức năng đổi mật khẩu)
    @PutMapping("/{id}/password")
    public ResponseEntity<UserResponseDto> updateUserPassword(@PathVariable Long id, @RequestParam String newPassword) {
        UserResponseDto userResponseDto = userService.updateUserPassword(id, newPassword);
        return ResponseEntity.ok(userResponseDto);
    }
    //Dùng cho chức năng đăng nhâp và kiểm tra tài khoản là Admin (identifier = '666') hay Client
    @GetMapping("/login")
    public ResponseEntity<UserResponseDto> getUserByUsernameAndPassword(@RequestParam String username, @RequestParam String password) {
        UserResponseDto userResponseDto = userService.getUserByUsernameAndPassword(username, password);
        return ResponseEntity.ok(userResponseDto);
    }
}
