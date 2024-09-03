package com.example.balancing.dtos.station;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO станции. Этот класс инкапсулирует данные о станции, включая ее уникальный
 * идентификатор
 *
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>id:</b> Уникальный идентификатор станции.</li>
 *   <li><b>name:</b> Название станции (например, ГЭС, АЭС, ГРЭС).</li>
 * </ul>
 */
@Data
@Schema(description = "DTO станции в упрощенном представлении")
public class SimplifiedStationDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Название станции", example = "Саяно-Шушенская ГЭС")
    private String name;

}
