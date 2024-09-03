package com.example.balancing.dtos.station;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * DTO станции. Этот класс инкапсулирует данные о станции, включая ее уникальный
 * идентификатор, название и список связанных агрегатов.
 *
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>id:</b> Уникальный идентификатор станции.</li>
 *   <li><b>name:</b> Название станции (например, ГЭС, АЭС, ГРЭС).</li>
 *   <li><b>units_id:</b> Список уникальных идентификаторов агрегатов, связанных со станцией.</li>
 *   <li><b>createdAt:</b> Дата создания.</li>
 *   <li><b>updatedAt:</b> Дата последнего обновления.</li>
 * </ul>
 */
@Data
@Schema(description = "DTO станции")
public class StationDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Название станции", example = "Саяно-Шушенская ГЭС")
    private String name;

    @Schema(description = "Список уникальных идентификаторов агрегатов")
    private List<Long> units_id;

    @Schema(description = "Дата создания")
    private Date createdAt;

    @Schema(description = "Дата последнего обновления")
    private Date updatedAt;

}
