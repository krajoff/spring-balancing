package com.example.balancing.dto.plane;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
@Schema(description = "DTO плоскости с грузами")
public class PlaneDto {

    @Schema(description = "Номер плоскости", example = "1")
    @DecimalMin(value = "0", message = "Значение не может принимать отрицательную величину")
    private Integer number;

}
