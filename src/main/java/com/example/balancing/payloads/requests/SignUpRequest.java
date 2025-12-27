package com.example.balancing.payloads.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "username", example = "Nikolay")
    @Size(min = 5, max = 30, message = "[Username] Минимальная длина — 5 символов, максимальная — 30")
    @NotBlank(message = "[Username] Не может быть пустым")
    private String username;

    @Schema(description = "email", example = "nikolay@gmail.com")
    @NotBlank(message = "[Email] Не может быть пустым")
    @Email(message = "[Email] Формат должен соответствовать паттерну user@example.com")
    private String email;

    @Schema(description = "password")
    @Size(min = 5, max = 255, message = "[Password] Минимальная длина пароля — 5 символов, максимальная — 255")
    private String password;
}
