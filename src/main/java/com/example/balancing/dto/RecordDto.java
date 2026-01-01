package com.example.balancing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.numbers.complex.Complex;

import java.util.UUID;

@Data
@Schema(description = "DTO записи вибрации")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordDto {

    @Schema(description = "Уникальный номер записи")
    private UUID id;

    @NotNull
    @Size(min = 1, max = 15, message = "Значение не может быть пустым или длиннее 15 знаков")
    @Schema(description = "Название места измерения вибрации в упрощенном представлении", example = "ВГП или 1")
    private String pointName;

    @NotNull
    @Size(min = 1, max = 30, message = "Значение не может быть пустым или длиннее 30 знаков")
    @Schema(description = "Режима работы агрегата в упрощенном представлении", defaultValue = "No-load 100%n")
    private String modeName;

    @NotNull
    @Schema(description = "Значение амплитуды вибрации", example = "123.1")
    @DecimalMin(value = "0.0", message = "Значение не может принимать отрицательную величину")
    private Double magVibration;

    @NotNull
    @Schema(description = "Значение фазы вибрации в градусах (deg)", example = "180.0")
    @DecimalMax(value = "360.0", message = "Значение не должно быть больше 360 градусов")
    @DecimalMin(value = "-360.0", message = "Значение не должно быть меньше -360 градусов")
    private Double phaseVibration;

    @Schema(description = "Комплексное значение вибрации", example = "10.0+13.1i")
    private Complex complexVibration;

    @JsonSetter(nulls = Nulls.SKIP)
    @Schema(description = "Флаг использования в оптимизационном расчете", example = "true", defaultValue = "true")
    private Boolean isUsed = true;

    @JsonSetter(nulls = Nulls.SKIP)
    @Schema(description = "Флаг использования ручного задания чувствительности", example = "false", defaultValue = "false")
    private Boolean isManualSensitivity = false;

    @Schema(description = "Значение амплитуды чувствительности", example = "4.1")
    private Double magSensitivity;

    @Schema(description = "Значение фазы чувствительности", example = "181.1")
    private Double phaseSensitivity;

    @Schema(description = "Комплексное значение чувствительности", example = "181.1+10i")
    private Complex complexSensitivity;

    public Complex getComplexVibration() {
        if (magVibration == null || phaseVibration == null) return null;
        this.complexVibration = Complex.ofPolar(this.magVibration, Math.toRadians(this.phaseVibration));
        return complexVibration;
    }

    public Complex getComplexSensitivity() {
        if (magSensitivity == null || phaseSensitivity == null) return null;
        this.complexSensitivity = Complex.ofPolar(this.magSensitivity, Math.toRadians(this.phaseSensitivity));
        return complexVibration;
    }

}
