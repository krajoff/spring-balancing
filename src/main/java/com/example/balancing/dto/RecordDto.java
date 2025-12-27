package com.example.balancing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.apache.commons.numbers.complex.Complex;

@Data
@Schema(description = "DTO записи вибрации")
public class RecordDto {

    @Size(min = 1, max = 15, message = "Значение не может быть пустым или длиннее 15 знаков")
    @Schema(description = "Название места измерения вибрации в упрощенном представлении", example = "ВГП или 1")
    private String pointName;

    @Size(min = 1, max = 15, message = "Значение не может быть пустым или длиннее 15 знаков")
    @Schema(description = "Режима работы агрегата в упрощенном представлении", defaultValue = "No-load 100%n")
    private String modeName;

    @Schema(description = "Значение амплитуды вибрации", example = "123.1")
    @DecimalMin(value = "0.0", message = "Значение не может принимать отрицательную величину")
    private Double magVibration;

    @Schema(description = "Значение фазы вибрации в градусах (deg)", example = "180.0")
    @DecimalMax(value = "360.0", message = "Значение не должно быть больше 360 градусов")
    @DecimalMin(value = "-360.0", message = "Значение не должно быть меньше -360 градусов")
    private Double phaseVibration;

    @Schema(description = "Комплексное значение вибрации", example = "10.0+13.1i")
    private Complex complexVibration;

    @Schema(description = "Флаг использования в оптимизационном расчете", example = "true")
    private Boolean isUsed;

    @Schema(description = "Флаг использования ручного задания чувствительности", example = "false")
    private Boolean isManualSensitivity;

    @Schema(description = "Значение амплитуды чувствительности", example = "4.1")
    private Double magSensitivity;

    @Schema(description = "Значение фазы чувствительности", example = "181.1")
    private Double phaseSensitivity;

    @Schema(description = "Комплексное значение чувствительности", example = "181.1+10i")
    private Complex complexSensitivity;

    public Complex getComplexVibration() {
        this.complexVibration = Complex.ofPolar(this.magVibration, Math.toRadians(this.phaseVibration));
        return complexVibration;
    }

    public Complex getComplexSensitivity() {
        this.complexSensitivity = Complex.ofPolar(this.magSensitivity, Math.toRadians(this.phaseSensitivity));
        return complexVibration;
    }

}
