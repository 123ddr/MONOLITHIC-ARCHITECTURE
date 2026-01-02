package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO;


import lombok.Data;

@Data
public class UserCreateRequest {
    private String username;
    private String email;
    private String password;
    private String role;
}

