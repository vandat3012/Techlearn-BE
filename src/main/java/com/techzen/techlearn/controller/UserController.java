package com.techzen.techlearn.controller;

import com.techzen.techlearn.dto.request.UserRequestDTO;
import com.techzen.techlearn.dto.response.ResponseData;
import com.techzen.techlearn.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/users")
public class UserController {

    UserService userService;

    @GetMapping("/{id}")
    public ResponseData<?> getUserById(@PathVariable UUID id) {
        return ResponseData.builder().status(HttpStatus.OK.value()).message("Get user success").data(userService.getUserById(id)).build();
    }

    @PostMapping
    public ResponseData<?> addUser(@RequestBody @Valid UserRequestDTO request) {
        return ResponseData.builder().status(HttpStatus.OK.value()).message("Get user success").data(userService.addUser(request)).build();
    }

    @PatchMapping
    public ResponseData<?> updateUser(@RequestParam UUID id, @RequestBody UserRequestDTO request) {
        return ResponseData.builder().status(HttpStatus.OK.value()).message("Update user success").data(userService.updateUser(id, request)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseData.builder().status(HttpStatus.OK.value()).message("Delete user success").build();
    }

    @GetMapping("/list")
    public ResponseData<?> getAllUser(@RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return ResponseData.builder().status(HttpStatus.OK.value()).message("Get all user success").data(userService.getAllUser(page, pageSize)).build();
    }
}
