package com.example.balancing.dtos.weight;

import com.example.balancing.dtos.plane.PlaneDto;
import com.example.balancing.dtos.record.RecordDto;
import com.example.balancing.models.complex.Complex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;

/**
 * DTO груза
 * Этот класс инкапсулирует данные о грузе, плоскости его установки,
 * фазе, а также дополнительную системную информацию и список записей вибрации.
 * <p><b>Поля:</b></p>
 * <ul>
 *   <li><b>id:</b> Уникальный идентификатор груза.</li>
 *   <li><b>plane:</b> Плоскость, на которой установлен груз.</li>
 *   <li><b>run:</b> Номер пуска, связанный с установкой данного груза.</li>
 *   <li><b>reference:</b> Ссылка на референсный вес.</li>
 *   <li><b>magWeight:</b> Значение массы груза.</li>
 *   <li><b>phaseWeight:</b> Значение фазы установки груза в градусах.</li>
 *   <li><b>complexWeight:</b> Комплексное значение веса (с компонентами реального и мнимого числа).</li>
 *   <li><b>unit_id:</b> Агрегат, с которым связан данный груз.</li>
 *   <li><b>records:</b> Список записей вибрации, выполненных при данном грузе.</li>
 *   <li><b>systemInformation:</b> Системная информация о грузе.</li>
 *   <li><b>isTarget:</b> Флаг, указывающий, является ли данный вес целевым.</li>
 * </ul>
 */
@Data
@Schema
public class WeightDto {

    @Schema(description = "Уникальный идентификатор",
            example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Плоскость, на которой установлен груза",
            example = "1, 2 и т.д.")
    private PlaneDto plane;

    @Schema(description = "Номер пуска", example = "1, 2, и т.д.")
    private Integer run;

    @Schema(description = "Значение веса", example = "100.0")
    private Double magWeight;

    @Schema(description = "Значение фазы установки веса в градусах (deg)",
            example = "180.0")
    private Double phaseWeight;

    @Schema(description = "Комплексное значение веса", example = "10.0+13.1i")
    private Complex complexWeight;

    @Schema(description = "Значение общего веса", example = "100.0")
    private Double magTotalWeight;

    @Schema(description = "Значение фазы установки общего веса в градусах (deg)",
            example = "180.0")
    private Double phaseTotalWeight;

    @Schema(description = "Комплексное значение общего веса", example = "10.0+13.1i")
    private Complex complexTotalWeight;

    @Schema(description = "Список записей вибрации, выполненные при данном весе")
    private List<RecordDto> records;

    @Schema(description = "Системная информация")
    private String systemInformation;

    @Schema(description = "Флаг, указывающий является ли данный вес целевым")
    private boolean isTarget;

}
