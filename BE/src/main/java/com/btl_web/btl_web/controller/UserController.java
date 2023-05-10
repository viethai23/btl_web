package com.btl_web.btl_web.controller;

import com.btl_web.btl_web.model.dto.UserRequestDto;
import com.btl_web.btl_web.model.dto.UserResponseDto;
import com.btl_web.btl_web.service.UserService;
import com.btl_web.btl_web.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.Validate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final Validation validation;

    @Autowired
    public UserController(UserService userService, Validation validation) {
        this.userService = userService;
        this.validation = validation;
    }
    //Thêm 1 user (dùng cho chức năng đăng ký tài khoản)
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserRequestDto userRequestDto) {
        List<String> list_error = validation.getInputError(userRequestDto);
        if (!list_error.isEmpty()){
            return new ResponseEntity<>(list_error, HttpStatus.BAD_REQUEST);
        }
        UserResponseDto userResponseDto = userService.addUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }
    //Thay đổi mật khẩu (dùng cho chức năng đổi mật khẩu)
    @PutMapping("/{id}/password")
    public ResponseEntity<UserResponseDto> updateUserPassword(@PathVariable Long id, @RequestParam String newPassword) {
        UserResponseDto userResponseDto = userService.updateUserPassword(id, newPassword);
        return ResponseEntity.ok(userResponseDto);
    }
    //Dùng cho chức năng đăng nhâp và kiểm tra tài khoản là Admin (identifier = '666') hay User
    @GetMapping("/login")
    public ResponseEntity<UserResponseDto> getUserByUsernameAndPassword(@RequestParam String username, @RequestParam String password) {
        UserResponseDto userResponseDto = userService.getUserByUsernameAndPassword(username, password);
        return ResponseEntity.ok(userResponseDto);
    }
    // Lấy tất cả User
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> Users = userService.getAllUsers();
        return ResponseEntity.ok(Users);
    }
    // Lấy User theo id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto User = userService.getUserById(id);
        return ResponseEntity.ok(User);
    }
    // Sửa User
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        List<String> list_error = validation.getInputError(requestDto);
        if (!list_error.isEmpty()){
            return new ResponseEntity<>(list_error, HttpStatus.BAD_REQUEST);
        }
        UserResponseDto user = userService.updateUser(id, requestDto);
        return ResponseEntity.ok(user);
    }
    // Xóa User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
