package com.example.balancing.dtos.mode;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * DTO режима работы агрегата. Этот класс инкапсулирует данные о режиме,
 * включая ее уникальный идентификатор, имя и список связанных записей.
 *
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>id:</b> Уникальный идентификатор режима.</li>
 *   <li><b>name:</b> Название режима.</li>
 *   <li><b>records:</b> Список записей (вибраций, измерений и т.д.),
 *   связанных с точкой.</li>
 * </ul>
 */
@Data
@Schema(description = "DTO режима работы агрегата в упрощенном предсталении")
public class SimplifiedModeDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Название режима", example = "Точка 1")
    private String name;

    @Schema(description = "Список id записей, связанных с режимом")
    private List<Long> recordIds;
}
