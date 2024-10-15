package com.example.balancing.dtos.station;

import com.example.balancing.dtos.unit.SimplifiedUnitDto;
import com.example.balancing.models.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * DTO станции. Этот класс инкапсулирует данные о станции, включая ее уникальный
 * идентификатор, название, список связанных агрегатов и их количество.
 *
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>id:</b> Уникальный идентификатор станции.</li>
 *   <li><b>name:</b> Название станции.</li>
 *   <li><b>units_id:</b> Список уникальных идентификаторов агрегатов, связанных со станцией.</li>
 *   <li><b>counter:</b> Число агрегатов входящих в состав станции.</li>
 *   <li><b>createdAt:</b> Дата создания.</li>
 *   <li><b>updatedAt:</b> Дата последнего обновления.</li>
 * </ul>
 */
@Data
@Schema(description = "DTO станции")
public class StationDto {

    @Schema(description = "Уникальный идентификатор станции", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Название станции", example = "Саяно-Шушенская ГЭС")
    private String name;

    @Schema(description = "Агрегаты, относящиеся к станции")
    private List<SimplifiedUnitDto> units;

    @Schema(description = "Дата создания")
    private Date createdAt;

    @Schema(description = "Дата последнего обновления")
    private Date updatedAt;

}
