package com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Controller;


import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.UserCreateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.UserResponse;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.DTO.UserUpdateRequest;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Service.IUserService;
import com.MONOLITHIC_ARCHITECTURE.MONOLITHIC_ARCHITECTURE.Response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /* ================= ADMIN ENDPOINTS ================= */

    // CREATE USER (ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> createUser(@RequestBody UserCreateRequest request) {
        try {
            UserResponse user = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Response.success(HttpStatus.CREATED, user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.error(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    // GET USER BY ID (ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        try {
            UserResponse user = userService.getUserById(id);
            return ResponseEntity.ok(Response.success(HttpStatus.OK, user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.error(HttpStatus.NOT_FOUND, e.getMessage()));
        }
    }

    // GET ALL USERS (ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        try {
            List<UserResponse> users = userService.getAllUsers();
            return ResponseEntity.ok(Response.success(HttpStatus.OK, users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.error(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    // UPDATE USER (ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> updateUser(@PathVariable Long id,
                                               @RequestBody UserUpdateRequest request) {
        try {
            UserResponse updatedUser = userService.updateUser(id, request);
            return ResponseEntity.ok(Response.success(HttpStatus.OK, updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.error(HttpStatus.NOT_FOUND, e.getMessage()));
        }
    }

    // DELETE USER (ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(Response.success(HttpStatus.OK, "User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.error(HttpStatus.NOT_FOUND, e.getMessage()));
        }
    }

    /* ================= USER ENDPOINTS ================= */

    // GET CURRENT LOGGED-IN USER
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> getCurrentUser() {
        try {
            UserResponse user = userService.getCurrentUser();
            return ResponseEntity.ok(Response.success(HttpStatus.OK, user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Response.error(HttpStatus.UNAUTHORIZED, e.getMessage()));
        }
    }

    // UPDATE CURRENT LOGGED-IN USER
    @PutMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response> updateCurrentUser(@RequestBody UserUpdateRequest request) {
        try {
            UserResponse updatedUser = userService.updateCurrentUser(request);
            return ResponseEntity.ok(Response.success(HttpStatus.OK, updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Response.error(HttpStatus.UNAUTHORIZED, e.getMessage()));
        }
    }
}

