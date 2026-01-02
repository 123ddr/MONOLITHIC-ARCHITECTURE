package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO;


import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String password;
    private String email;
    private String role;
}

