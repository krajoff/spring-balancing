package com.example.balancing.dto.run;

import com.example.balancing.dto.weight.WeightDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import lombok.Data;

@Data
@Schema(description = "DTO пуска агрегата")
public class RunDto {

    @Schema(description = "Номер пуска", example = "0", defaultValue = "0")
    @DecimalMax(value = "0", message = "Значение не может принимать отрицательную величину")
    private Integer number;

    @Schema(description = "Плоскость установки груза", example = "0", defaultValue = "0")
    @DecimalMax(value = "0", message = "Значение не может принимать отрицательную величину")
    private Integer plane;

    @Schema(description = "Груз установленный на плоскость", example = "100")
    private WeightDto weight;

    @Schema(description = "Ссылка на номер предыдущего пуска", example = "0")
    private Long referenceRunId;

}
