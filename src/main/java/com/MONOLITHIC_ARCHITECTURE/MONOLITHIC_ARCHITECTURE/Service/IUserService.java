package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.UserCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.UserResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.UserUpdateRequest;

import java.util.List;

public interface IUserService {

    // Admin operations
    UserResponse createUser(UserCreateRequest request);
    UserResponse updateUser(Long userId, UserUpdateRequest request);
    void deleteUser(Long userId);
    UserResponse getUserById(Long userId);
    List<UserResponse> getAllUsers();

    // User operations
    UserResponse getCurrentUser();                 // Get logged-in user info
    UserResponse updateCurrentUser(UserUpdateRequest request);  // Update own info
}

