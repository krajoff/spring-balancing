package com.example.balancing.dto.mode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO режима работы агрегата")
public class ModeDto {

    @Schema(description = "Название режима", example = "ХХТ 100%n")
    private String name;

}
