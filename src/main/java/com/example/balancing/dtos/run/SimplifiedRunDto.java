package com.example.balancing.dtos.run;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Упрощенное DTO пуска агрегата")
public class SimplifiedRunDto {

    @Schema(description = "Уникальный идентификатор", example = "1, 2 и т.д.")
    private Long id;

    @Schema(description = "Номер пуска", example = "1")
    private Integer number;

}
