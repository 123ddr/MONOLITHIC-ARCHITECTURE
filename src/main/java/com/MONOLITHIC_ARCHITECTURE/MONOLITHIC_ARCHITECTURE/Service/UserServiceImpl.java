package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Config.SecurityUtils;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.UserCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.UserResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.UserUpdateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Entity.UserEntity;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils; // helper for current user

    @Autowired
    public UserServiceImpl(UserRepo userRepo,
                           PasswordEncoder passwordEncoder,
                           SecurityUtils securityUtils) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.securityUtils = securityUtils;
    }

    /* ================= ADMIN ================= */
    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepo.save(user);
        return toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND"));

        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRole() != null) user.setRole(request.getRole());

        userRepo.save(user);
        return toResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND"));
        return toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /* ================= USER ================= */
    @Override
    public UserResponse getCurrentUser() {
        String username = securityUtils.getCurrentUsername();
        if (username == null) throw new RuntimeException("User not authenticated");

        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND"));

        return toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateCurrentUser(UserUpdateRequest request) {
        String username = securityUtils.getCurrentUsername();
        if (username == null) throw new RuntimeException("User not authenticated");

        UserEntity user = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("USER NOT FOUND"));

        // Users cannot change role
        request.setRole(null);

        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepo.save(user);
        return toResponse(user);
    }

    /* ================= HELPER ================= */
    private UserResponse toResponse(UserEntity entity) {
        UserResponse dto = new UserResponse();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        return dto;
    }
}

