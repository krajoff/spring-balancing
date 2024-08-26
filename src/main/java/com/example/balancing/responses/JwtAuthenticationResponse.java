package com.example.balancing.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Access token response")
public class JwtAuthenticationResponse {
    @Schema(description = "Token response")
    private String token;
}
