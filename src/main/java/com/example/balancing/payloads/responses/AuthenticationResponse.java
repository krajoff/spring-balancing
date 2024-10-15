package com.example.balancing.payloads.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Объект ответа для аутентификации.
 * <p>
 * Этот класс ответа, отправленный клиенту после успешной аутентификации,
 * который включает аксес-токен доступа.
 * </p>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Access and refresh tokens response")
public class AuthenticationResponse {

    @Schema(description = "Token access")
    @NotNull private String accessToken;

    @Schema(description = "Type")
    private final String type = "Bearer";

    @Schema(description = "Token refresh")
    @NotNull private String refreshToken;

}
