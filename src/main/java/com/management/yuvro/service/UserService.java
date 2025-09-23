package com.management.yuvro.service;

import com.management.yuvro.dto.request.LoginRequest;
import com.management.yuvro.dto.request.RegisterRequest;
import com.management.yuvro.dto.request.ResetPasswordRequest;
import com.management.yuvro.dto.response.AuthResponse;
import com.management.yuvro.dto.response.CommonApiResponse;

public interface UserService {
    CommonApiResponse registerUser(RegisterRequest request);

    CommonApiResponse deleteUser(Long userId);

    CommonApiResponse resetPassword(Long userId, ResetPasswordRequest request);

    AuthResponse authenticateUser(LoginRequest request);
}
