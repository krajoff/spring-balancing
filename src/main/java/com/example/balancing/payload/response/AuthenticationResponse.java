package com.example.balancing.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Access and refresh tokens response")
public class AuthenticationResponse {

    @Schema(description = "Token access")
    @NotNull(message = "[Access token] Can't be empty")
    private String accessToken;

    @Schema(description = "Type")
    private final String type = "Bearer";

    @Schema(description = "Token refresh")
    @NotNull(message = "[Refresh token] Can't be empty")
    private String refreshToken;

}
