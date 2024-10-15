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
            message = "Minimum username length is 5 letters, maximum is 30")
    @NotBlank(message = "Username can not be blank ")
    private String username;

    @Schema(description = "password")
    @NotBlank(message = "Password can not be blank ")
    @Size(min = 5, max = 255,
            message = "Minimum password length is 5 letters, maximum is 255")
    private String password;
}
