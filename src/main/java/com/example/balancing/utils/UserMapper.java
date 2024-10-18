package com.example.balancing.utils;

import com.example.balancing.dtos.user.UserDto;
import com.example.balancing.models.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями User и UserDto:
 * UserDto содержит stationsDto, email.
 */
@Mapper(componentModel = "spring", uses = StationMapper.class)
public abstract class UserMapper {

    /**
     * Преобразует сущность User в UserDto.
     *
     * @param user сущность User
     * @return объект UserDto
     */
    @Mapping(source = "stations", target = "stations")
    public abstract UserDto userToUserDto(User user);

    /**
     * Преобразует сущность User в UserDto.
     *
     * @param userDto сущность UserDto
     * @return объект User
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordConfirm", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(source = "stations", target = "stations")
    public abstract User userDtoToUser(UserDto userDto);

}

