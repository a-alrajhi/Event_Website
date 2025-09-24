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
@Schema(
        name = "RegisterRequest",
        description = "Request payload for user registration including authentication and personal details"
)
public class RegisterRequest extends AuthRequest {

    @NotNull
    @Size(min = 2, max = 100)
    @Schema(description = "The user's full name", example = "John Doe", required = true)
    private String name;

    @Size(max = 20)
    @Schema(description = "The user's phone number", example = "+1234567890")
    private String phoneNumber;
}
