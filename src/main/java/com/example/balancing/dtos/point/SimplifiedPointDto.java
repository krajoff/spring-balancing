package com.example.balancing.dtos.point;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO точки измерения. Этот класс инкапсулирует данные о точке
 * измерения, включая только имя.
 */
@Data
@Schema(description = "Упрощенная DTO точки измерения")
public class SimplifiedPointDto {

    @Schema(description = "Название точки", example = "Точка 1")
    private String name;

}
