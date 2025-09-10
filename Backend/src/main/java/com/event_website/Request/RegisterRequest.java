package com.event_website.Request;

import com.event_website.Entity.User;
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
public class RegisterRequest extends AuthRequest {
    @NotNull
    @Schema(description = "The user's name")
    private String name;
    @Schema(description = "the user's phone number")
    private String phoneNumber;
}
