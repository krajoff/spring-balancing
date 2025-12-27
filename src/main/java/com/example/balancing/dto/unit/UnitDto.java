package com.example.balancing.dto.unit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Schema(description = "DTO агрегата/машины")
public class UnitDto {

    @Schema(description = "Номер агрегата на станции", example = "1, 2 и т.д.", defaultValue = "1")
    @Size(max = 3)
    private Integer unitNumber;

    @Schema(description = "Тип агрегата", example = "СВ 477/180-16 УХЛ4")
    @Size(max = 50)
    private String unitType;

    @Schema(description = "Количество знаков после запятой при отображении значений грузов", example = "0", defaultValue = "0")
    @DecimalMax(value = "5", message = "Точность выше пяти знаков после запятой недоступна")
    private Integer weightPrecision;

    @Schema(description = "Единица измерения грузов", example = "г, кг", defaultValue = "кг")
    @Size(max = 5)
    private String weightUnitMeasure;

    @Schema(description = "Количество знаков после запятой при отображении значений вибрации", example = "0", defaultValue = "0")
    @DecimalMax(value = "5", message = "Точность выше пяти знаков после запятой недоступна")
    private Integer vibrationPrecision;

    @Schema(description = "Единица измерения вибрации", example = "мкм, мм/с", defaultValue = "мм/с")
    @Size(max = 5)
    private String vibrationUnitMeasure;

    @Schema(description = "Дополнительное описание", example = "Измерения после ремонта")
    @Size(max = 255)
    private String description;

    @Schema(description = "Дата создания")
    private LocalDateTime createdOn;

    @Schema(description = "Дата обновления")
    private LocalDateTime updatedOn;

}
