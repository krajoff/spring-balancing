package com.example.balancing.dtos.plane;

import com.example.balancing.dtos.weight.WeightDto;
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
@Schema(description = "DTO плоскости с грузами")
public class PlaneDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Номер плоскости", example = "1")
    private Integer number;

    @Schema(description = "Список грузов, установленых на плоскости")
    private List<WeightDto> weights;
}
