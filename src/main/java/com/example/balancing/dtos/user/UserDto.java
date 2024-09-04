package com.example.balancing.dtos.user;

import com.example.balancing.dtos.station.StationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


/**
 * DTO пользователя системы.
 *  * Класс инкапсулирует основную информацию о пользователе, включая
 *  * его уникальный идентификатор, имя, электронную почту и список
 *  * агрегатов, связанных с пользователем.
 *  *
 *  * <p><b>Поля:</b></p>
 *  * <ul>
 *  *   <li><b>id:</b> Уникальный идентификатор пользователя.</li>
 *  *   <li><b>username:</b> Имя пользователя, используемое для идентификации в системе.</li>
 *  *   <li><b>email:</b> Электронная почта пользователя.</li>
 *  *   <li><b>stations:</b> Список станций, связанных с пользователем.</li>
 *  * </ul>
 *  */
@Data
@Schema(description = "DTO пользователя")
public class UserDto {

    @Schema(description = "Уникальный идентификатор", example = "654")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Nikolay_Petrovich")
    private String username;

    @Schema(description = "Электронная почта пользователя",
            example = "nikolay.jashin@mail.com")
    private String email;

    @Schema(description = "Список станций пользователя")
    private List<StationDto> stations;
}

