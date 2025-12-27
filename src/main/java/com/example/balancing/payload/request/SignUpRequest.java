package com.example.balancing.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request to sign up")
public class SignUpRequest {

    @Schema(description = "username", example = "Nikolay")
    @Size(min = 5, max = 30, message = "[Username] Minimum length is 5, maximum — 30")
    @NotBlank(message = "[Username] Can't be empty")
    private String username;

    @Schema(description = "email", example = "nikolay@gmail.com")
    @NotBlank(message = "[Email] Can't be empty")
    @Email(message = "[Email] The format should follow the pattern user@example.com")
    private String email;

    @Schema(description = "password")
    @Size(min = 5, max = 255, message = "[Password] Minimum length is 5, maximum — 255")
    private String password;
}
