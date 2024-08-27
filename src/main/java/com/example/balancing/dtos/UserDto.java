package com.example.balancing.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


/**
 * DTO для передачи данных о пользователе.
 * Содержит основную информацию о пользователе, используемую для
 * обмена данными между слоями приложения.
 */
@Data
@Schema(description = "DTO пользователя")
public class UserDto {

    @Schema(description = "Уникальный идентификатор пользователя", example = "654")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Nikolay_Petrovich")
    private String username;

    @Schema(description = "Электронная почта пользователя",
            example = "nikolay.jashin@mail.com")
    private String email;

    @Schema(description = "Список агрегатов пользователя")
    private List<UnitDto> units;
}

