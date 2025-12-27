package com.example.balancing.dto;

import com.example.balancing.entity.run.RunParameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
@Schema(description = "DTO пуска агрегата")
public class RunDto {

    @Schema(description = "Номер пуска", example = "0", defaultValue = "0")
    @DecimalMin(value = "0", message = "Значение не может принимать отрицательную величину")
    private Integer runNumber;

    @Schema(description = "Ссылка на номер предыдущего пуска", example = "0")
    private Long referenceRunId;

    @Schema(description = "Параметры пуска")
    private RunParameter runsParameters;

}
