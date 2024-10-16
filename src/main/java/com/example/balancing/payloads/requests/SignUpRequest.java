package com.example.balancing.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Объект передачи данных для запросов на регистрацию пользователей.
 * <p>
 * Этот класс инкапсулирует данные, необходимые новому пользователю для
 * регистрации. Он включает ограничения валидации, чтобы убедиться,
 * что предоставленные имя пользователя, электронная почта и пароль соответствуют
 * требуемому формату и длине.
 * </p>
 */
@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @JsonProperty("username")
    @Schema(description = "username", example = "Nikolay")
    @Size(min = 5, max = 30,
            message = "Minimum username length is 5 letters, maximum is 30")
    @NotBlank(message = "Username can not be blank ")
    private String username;

    @JsonProperty("email")
    @Schema(description = "email", example = "nikolay@gmail.com")
    @Size(min = 5, max = 255,
            message = "Minimum email length is 5 letters, maximum is 255")
    @NotBlank(message = "Email can not be blank")
    @Email(message = "Email address should be in the format user@example.com")
    private String email;

    @JsonProperty("password")
    @Schema(description = "password")
    @Size(min = 5, max = 255,
            message = "Minimum password length is 5 letters, maximum is 255")
    private String password;
}
