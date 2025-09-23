package com.management.yuvro.controller;

import com.management.yuvro.dto.request.LoginRequest;
import com.management.yuvro.dto.request.RegisterRequest;
import com.management.yuvro.dto.request.ResetPasswordRequest;
import com.management.yuvro.dto.response.AuthResponse;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.management.yuvro.constants.Constants.ADMIN_ROLE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rs/auth/")
public class UserController {

    private final UserService userService;

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<CommonApiResponse> deleteUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.deleteUser(userId));
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")

//    @PreAuthorize("hasRole('ADMIN_ROLE')")
    @PreAuthorize("hasRole(T(com.management.yuvro.constants.Constants).ADMIN_ROLE)")
    @PutMapping("/users/{userId}/reset-password")
    public ResponseEntity<CommonApiResponse> updatePassword(@RequestBody ResetPasswordRequest request, @PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.resetPassword(userId, request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok().body(userService.authenticateUser(request));
    }

    // Endpoint to create user and assign role
    @PostMapping("/users")
    public ResponseEntity<CommonApiResponse> createUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok().body(userService.registerUser(request));
    }
}
