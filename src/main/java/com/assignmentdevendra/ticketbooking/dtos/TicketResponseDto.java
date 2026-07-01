package com.assignmentdevendra.ticketbooking.dtos;

import com.assignmentdevendra.ticketbooking.model.TicketStatus;
import lombok.Data;

@Data
public class TicketResponseDto {

    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private String userUsername;
}