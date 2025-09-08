package com.event_website.Request;

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
public class RegisterRequest {
    @NotNull
    private String password;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String name;
    private String phoneNumber;
}
