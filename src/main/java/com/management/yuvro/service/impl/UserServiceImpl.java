package com.management.yuvro.service.impl;

import com.management.yuvro.dto.request.LoginRequest;
import com.management.yuvro.dto.request.RegisterRequest;
import com.management.yuvro.dto.request.ResetPasswordRequest;
import com.management.yuvro.dto.response.AuthResponse;
import com.management.yuvro.dto.response.CommonApiResponse;
import com.management.yuvro.exceptions.BadRequestException;
import com.management.yuvro.jpa.repository.YuvroUserRepository;
import com.management.yuvro.mapper.UserMapper;
import com.management.yuvro.service.UserService;
import com.management.yuvro.utils.EmailUtils;
import com.management.yuvro.utils.EncryptionUtils;
import com.management.yuvro.utils.KeycloakUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.management.yuvro.utils.PasswordGenerator.generateRandomPassword;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final KeycloakUtils keycloakUtils;
    private final YuvroUserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailUtils emailUtils;
    private final String username;

    public UserServiceImpl(
            KeycloakUtils keycloakUtils, YuvroUserRepository userRepository, UserMapper userMapper, EmailUtils emailUtils,
            @Value("${keycloak.username}") String username) {
        this.keycloakUtils = keycloakUtils;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.emailUtils = emailUtils;
        this.username = username;
    }

    @Override
    public CommonApiResponse registerUser(RegisterRequest request) {
        try {
            log.info("Registering user: {}", request.getUsername());

            if (userRepository.existsByUsername(request.getUsername())) {
                throw new BadRequestException("Username already exists");
            }

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email already exists");
            }

            String password = null;
            if (request.getUsername().equalsIgnoreCase(username)) {
                log.info("Assigning default password for admin user");
                password = username;
            } else {
                log.info("Generating random password for user: {}", request.getUsername());
                password = generateRandomPassword(10);
            }

            log.info("User {}: {}", request.getUsername(), password);

            log.info("Creating user in local database");
            var user = userMapper.mapRegisterRequestToYuvroUser(request);
            user.setPassword(EncryptionUtils.encrypt(password));
            user = userRepository.save(user);

            log.info("Creating user in Keycloak");
            keycloakUtils.createUser(request.getUsername(), request.getFirstName(), request.getLastName(), request.getEmail(), password, request.getType().toUpperCase());

            log.info("User registered successfully: {}", user.getUsername());

            if (!request.getUsername().equalsIgnoreCase(username)) {
                log.info("Sending welcome email to user: {}", request.getEmail());
                Map<String, String> model = new HashMap<>();
                model.put("username", request.getUsername());
                model.put("email", request.getEmail());
                model.put("password", password);
                emailUtils.sendWelcomeEmail(request.getEmail(), "Welcome to Yuvro", model);
            } else
                log.info("Skipping welcome email for default admin user: {}", username);

            return new CommonApiResponse("User registered successfully", true);
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage());
            return new CommonApiResponse("Error registering user: " + e.getMessage(), false);
        }
    }

    @Override
    public CommonApiResponse deleteUser(Long userId) {

        return null;
    }

    @Override
    public CommonApiResponse resetPassword(Long userId, ResetPasswordRequest request) {
        try {
            log.info("Resetting password for user: {}", userId);

            var user = userRepository.findById(userId)
                    .orElseThrow(() -> new BadRequestException("User with id " + userId + " does not exist"));

            if (EncryptionUtils.decrypt(user.getPassword()).equals(request.getOldPassword())) {
                log.info("Updating password in local database");
                user.setPassword(EncryptionUtils.encrypt(request.getNewPassword()));
                userRepository.save(user);
                log.info("Updating password in Keycloak");
                keycloakUtils.resetPasswordByUsername(user.getUsername(), request.getNewPassword());
                log.info("Password reset successfully for user: {}", user.getUsername());
            } else throw new BadRequestException("Old password does not match");

            return new CommonApiResponse("Password reset successfully", true);
        } catch (Exception e) {
            log.error("Error resetting password for user id {}: {}", userId, e.getMessage());
            return new CommonApiResponse("Error resetting password: " + e.getMessage(), false);
        }
    }

    @Override
    public AuthResponse authenticateUser(LoginRequest request) {

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("User with email " + request.getEmail() + " does not exist"));

        log.info("Authenticating user: {}", request.getEmail());
        var response = keycloakUtils.getToken(request.getEmail(), request.getPassword());
        userMapper.mapUserWithAuthResponse(user, response);
        response.setSuccess(true);
        response.setMessage("User authenticated successfully");
        return response;
    }
}
