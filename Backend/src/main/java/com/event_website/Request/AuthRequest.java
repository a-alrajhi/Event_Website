package com.event_website.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@Validated
@NoArgsConstructor
@Schema(name = "AuthRequest", description = "Login request with email and password")
public class AuthRequest {

    @NotNull
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "The user's password", example = "secret123")
    private String password;

    @NotNull
    @Email
    @Schema(description = "The user's email address", example = "user@example.com")
    private String email;

    @Override
    public String toString() {
        // Return email only for safety
        return "AuthRequest{email='" + email + "'}";
    }
}
