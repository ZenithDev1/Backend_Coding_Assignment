package com.assignmentdevendra.ticketbooking.controllers;

import com.assignmentdevendra.ticketbooking.dtos.AuthRequestDto;
import com.assignmentdevendra.ticketbooking.dtos.AuthResponseDto;
import com.assignmentdevendra.ticketbooking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequestDto request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
