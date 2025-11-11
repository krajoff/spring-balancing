package com.example.balancing.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "DTO пользователя")
public class UserDto {

    @Size(min = 3, max = 50)
    @Schema(description = "Имя пользователя", example = "Nikolay_Petrovich")
    private String username;

    @Size(min = 6, max = 255)
    @Schema(description = "Электронная почта пользователя", example = "nikolay.jashin@mail.com")
    private String email;

    @Schema(description = "Дата создания")
    private Date createdOn;

}

