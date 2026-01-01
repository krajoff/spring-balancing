package com.example.balancing.dto;

import com.example.balancing.entity.run.RunParameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "DTO пуска агрегата")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RunDto {

    @Schema(description = "Уникальный номер записи")
    private UUID id;

    @Schema(description = "Номер пуска", example = "0", defaultValue = "0")
    @Min(0)
    @Max(value = 10, message = "Число пусков больше 10 недоступно")
    private Integer runNumber;

    @Schema(description = "Ссылка на уникальный номер предыдущего пуска", example = "0")
    private UUID referenceRunId;

    @Schema(description = "Параметры пуска")
    private RunParameters runParameters;

}
