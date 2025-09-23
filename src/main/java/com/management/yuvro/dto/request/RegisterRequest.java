package com.management.yuvro.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Schema(description = "User type", example = "student/faculty/admin")
    private String type;

    @NotBlank(message = "Username cannot be blank")
    @NotNull(message = "Username cannot be null")
    @Schema(description = "Full name of the user", example = "Alice Johnson")
    private String username;

    @Schema(description = "First name of the user", example = "Alice")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Johnson")
    private String lastName;

    @Email
    @Schema(description = "Email address of the user", example = "alice.johnson@gmail.com")
    private String email;
}
