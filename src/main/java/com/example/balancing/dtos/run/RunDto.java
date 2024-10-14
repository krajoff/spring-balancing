package com.example.balancing.dtos.run;

import com.example.balancing.dtos.plane.PlaneDto;
import com.example.balancing.dtos.weight.WeightDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO пуска агрегата")
public class RunDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Номер пуска", example = "1")
    private Integer number;

    @Schema(description = "Плоскость установки груза", example = "1")
    private PlaneDto plane;

    @Schema(description = "Груз установленный на плоскость", example = "100")
    private WeightDto weight;

    @Schema(description = "Ссылка на номер предыдущего пуска", example = "0")
    private SimplifiedRunDto referenceRun;

}
