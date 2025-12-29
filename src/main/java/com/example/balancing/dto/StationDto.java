package com.example.balancing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Schema(description = "DTO станции")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StationDto {

    @Schema(description = "Уникальный номер записи")
    private UUID id;

    @Schema(description = "Название станции", example = "Саяно-Шушенская ГЭС", defaultValue = "Station")
    private String name;

    @Schema(description = "Агрегаты, относящиеся к станции")
    private List<UnitDto> units;

    @Schema(description = "Дата создания")
    private LocalDateTime createdOn;

    @Schema(description = "Дата последнего обновления")
    private LocalDateTime updatedOn;

}
