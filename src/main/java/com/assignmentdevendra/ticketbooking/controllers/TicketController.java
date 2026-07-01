package com.assignmentdevendra.ticketbooking.controllers;

import com.assignmentdevendra.ticketbooking.dtos.StatusUpdateRequestDto;
import com.assignmentdevendra.ticketbooking.dtos.TicketRequestDto;
import com.assignmentdevendra.ticketbooking.dtos.TicketResponseDto;
import com.assignmentdevendra.ticketbooking.model.User;
import com.assignmentdevendra.ticketbooking.services.TicketService;
import com.assignmentdevendra.ticketbooking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketRequestDto request, @AuthenticationPrincipal String username) {
        User user = userService.getCurrentUser(username);
        return ResponseEntity.ok(ticketService.createTicket(request, user));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getMyTickets(@AuthenticationPrincipal String username) {
        User user = userService.getCurrentUser(username);
        return ResponseEntity.ok(ticketService.getMyTickets(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable Long id, @AuthenticationPrincipal String username) {
        User user = userService.getCurrentUser(username);
        return ResponseEntity.ok(ticketService.getTicketById(id, user));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponseDto> updateTicketStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequestDto request,
            @AuthenticationPrincipal String username) {

        User user = userService.getCurrentUser(username);
        return ResponseEntity.ok(ticketService.updateTicketStatus(id, request.getStatus(), user));
    }
}