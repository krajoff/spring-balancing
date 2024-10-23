package com.example.balancing.payloads.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Объект передачи данных для запросов на регистрацию пользователя.
 * <p>
 * Этот класс инкапсулирует данные, необходимые пользователю для авторизации.
 * Он включает ограничения проверки, чтобы убедиться, что предоставленные
 * электронная почта и пароль соответствуют требуемому формату и длине.
 * </p>
 */

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "username", example = "Nikolay_Petrovich")
    @Size(min = 5, max = 30,
            message = "[Username] Минимальная длина — 5 символов, максимальная — 30")
    @NotBlank(message = "[Username] Не может быть пустым")
    private String username;

    @Schema(description = "password")
    @NotBlank(message = "[Password] Не может быть пустым")
    @Size(min = 5, max = 255,
            message = "[Password] Минимальная длина username — 5 символов, максимальная — 255")
    private String password;
}
