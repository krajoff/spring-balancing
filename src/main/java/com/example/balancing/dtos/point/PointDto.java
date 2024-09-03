package com.example.balancing.dtos.point;

import com.example.balancing.dtos.record.RecordDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * DTO точки измерения. Этот класс инкапсулирует данные о точке,
 * включая ее уникальный идентификатор, имя и список связанных записей.
 *
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>id:</b> Уникальный идентификатор точки.</li>
 *   <li><b>name:</b> Название точки.</li>
 *   <li><b>records:</b> Список записей (вибраций, измерений и т.д.),
 *   связанных с точкой.</li>
 * </ul>
 */
@Data
@Schema(description = "DTO точки измерения")
public class PointDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Название точки", example = "Точка 1")
    private String name;

    @Schema(description = "Список записей, связанных с точкой")
    private List<RecordDto> records;
}
