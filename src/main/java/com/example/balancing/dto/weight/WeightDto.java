package com.example.balancing.dto.weight;

import com.example.balancing.vo.complex.Complex;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
@Schema
public class WeightDto {

    @Schema(description = "Плоскость, на которой установлен груз", example = "1, 2 и т.д.")
    private Integer planeId;

    @Schema(description = "Значение веса", example = "100.0", defaultValue = "0.0")
    @DecimalMin(value = "0.0", message = "Вес не может быть отрицательным")
    private Double magWeight;

    @Schema(description = "Значение фазы установки веса в градусах (deg)", example = "180.0", defaultValue = "0.0")
    @DecimalMax(value = "360.0", message = "Значение не должно быть больше 360 градусов")
    @DecimalMin(value = "-360.0", message = "Значение не должно быть меньше -360 градусов")
    private Double phaseWeight;

    @Schema(description = "Комплексное значение веса", example = "10.0+13.1i")
    private Complex complexWeight;

    @Schema(description = "Системная информация")
    private String systemInformation;

    @Schema(description = "Флаг, указывающий является ли данный вес целевым")
    private Boolean isTarget;

}
