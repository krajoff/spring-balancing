package com.example.balancing.dtos.mode;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO режима работы агрегата. Этот класс инкапсулирует данные о режиме,
 * включая ее уникальный идентификатор, имя и список связанных записей.
 *
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>id:</b> Уникальный идентификатор режима.</li>
 *   <li><b>name:</b> Название режима.</li>
 * </ul>
 */
@Data
@Schema(description = "DTO режима работы агрегата в упрощенном представлении")
public class SimplifiedModeDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Название режима", example = "Точка 1")
    private String name;
}
