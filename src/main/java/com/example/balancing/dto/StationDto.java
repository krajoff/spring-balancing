package com.example.balancing.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "DTO станции")
public class StationDto {

    @Schema(description = "Название станции", example = "Саяно-Шушенская ГЭС", defaultValue = "Station")
    private String name;

    @Schema(description = "Агрегаты, относящиеся к станции")
    private List<UnitDto> units;

    @Schema(description = "Дата создания")
    private LocalDateTime createdOn;

    @Schema(description = "Дата последнего обновления")
    private LocalDateTime updatedOn;

}
