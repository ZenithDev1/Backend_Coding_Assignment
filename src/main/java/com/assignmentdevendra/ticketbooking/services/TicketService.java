package com.assignmentdevendra.ticketbooking.services;

import com.assignmentdevendra.ticketbooking.dtos.TicketRequestDto;
import com.assignmentdevendra.ticketbooking.dtos.TicketResponseDto;
import com.assignmentdevendra.ticketbooking.exceptions.InvalidStatusException;
import com.assignmentdevendra.ticketbooking.exceptions.ResourceNotFoundException;
import com.assignmentdevendra.ticketbooking.exceptions.UnauthorizedException;
import com.assignmentdevendra.ticketbooking.model.Ticket;
import com.assignmentdevendra.ticketbooking.model.TicketStatus;
import com.assignmentdevendra.ticketbooking.model.User;
import com.assignmentdevendra.ticketbooking.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    public TicketResponseDto createTicket(TicketRequestDto request, User user) {
        Ticket ticket = Ticket.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TicketStatus.open)
                .user(user)
                .build();
        return modelMapper.map(ticketRepository.save(ticket), TicketResponseDto.class);
    }

    public List<TicketResponseDto> getMyTickets(User user) {
        return ticketRepository.findByUser(user).stream()
                .map(ticket -> modelMapper.map(ticket, TicketResponseDto.class))
                .collect(Collectors.toList());
    }

    public TicketResponseDto getTicketById(Long id, User user) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        if (!ticket.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Access denied: You don't own this ticket");
        }
        return modelMapper.map(ticket, TicketResponseDto.class);
    }

    public TicketResponseDto updateTicketStatus(Long id, String statusStr, User user) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        if (!ticket.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Access denied: You don't own this ticket");
        }

        TicketStatus newStatus;
        try {
            newStatus = TicketStatus.valueOf(statusStr.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException("Invalid status value");
        }

        TicketStatus currentStatus = ticket.getStatus();

        if (currentStatus == TicketStatus.closed) {
            throw new InvalidStatusException("Closed ticket cannot be reopened");
        }
        if (currentStatus == TicketStatus.open && newStatus == TicketStatus.closed) {
            throw new InvalidStatusException("Cannot skip in_progress state from open");
        }
        if (currentStatus == TicketStatus.in_progress && newStatus == TicketStatus.open) {
            throw new InvalidStatusException("Cannot move back from in_progress to open");
        }

        ticket.setStatus(newStatus);
        return modelMapper.map(ticketRepository.save(ticket), TicketResponseDto.class);
    }
}