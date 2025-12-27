package com.example.balancing.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request to sign-in")
public class SignInRequest {

    @Schema(description = "username", example = "Nikolay_Petrovich")
    @Size(min = 5, max = 30, message = "[Username] Minimum length is 5, maximum — 30")
    @NotBlank(message = "[Username] Can't be empty")
    private String username;

    @Schema(description = "password")
    @NotBlank(message = "[Password] Can't be empty")
    @Size(min = 5, max = 255, message = "[Password] Minimum length is 5, maximum — 255")
    private String password;

}
