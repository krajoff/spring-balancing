package com.example.balancing.dtos.plane;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO режима работы агрегата. Этот класс инкапсулирует данные о плоскости уставки грузов,
 * включая ее уникальный идентификатор, номер и список связанных грузов.
 *
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>id:</b> Уникальный идентификатор.</li>
 *   <li><b>number:</b> Номер плоскости.</li>
 * </ul>
 */
@Data
@Schema(description = "DTO плоскости с грузами")
public class PlaneDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Номер плоскости", example = "1")
    private Integer number;

}
