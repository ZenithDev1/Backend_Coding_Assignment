package com.assignmentdevendra.ticketbooking.dtos;

import lombok.Data;

@Data
public class AuthRequestDto {

    private String username;
    private String password;
}