package com.assignmentdevendra.ticketbooking.services;

import com.assignmentdevendra.ticketbooking.config.JwtTokenProvider;
import com.assignmentdevendra.ticketbooking.dtos.AuthRequestDto;
import com.assignmentdevendra.ticketbooking.dtos.AuthResponseDto;
import com.assignmentdevendra.ticketbooking.exceptions.InvalidStatusException;
import com.assignmentdevendra.ticketbooking.exceptions.ResourceNotFoundException;
import com.assignmentdevendra.ticketbooking.model.User;
import com.assignmentdevendra.ticketbooking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public void register(AuthRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new InvalidStatusException("Username already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
    }

    public AuthResponseDto login(AuthRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid username or password");
        }

        String token = tokenProvider.generateToken(user.getUsername());
        return new AuthResponseDto(token);
    }

    public User getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}