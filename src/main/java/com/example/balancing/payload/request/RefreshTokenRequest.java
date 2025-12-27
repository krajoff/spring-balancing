package com.example.balancing.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "Refresh token request")
public class RefreshTokenRequest {

    @JsonProperty("refreshToken")
    @Schema(description = "Refresh token", example = "7175bda0-6ce0-48f4-a072-397a90ccef48")
    @NotBlank(message = "[Refresh Token] Refresh token can't be empty")
    private String refreshToken;

}
