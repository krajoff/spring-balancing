package com.example.balancing.utils;

import com.example.balancing.dtos.UserDto;
import com.example.balancing.models.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер для преобразования между сущностями User и UserDto:
 * UserDto содержит unitDto, email.
 */
@Mapper(componentModel = "spring", uses = UnitMapper.class)
public abstract class UserMapper {

    /**
     * Преобразует сущность User в UserDto.
     *
     * @param user сущность User
     * @return объект UserDto
     */
    @Mapping(source = "units", target = "units")
    public abstract UserDto userToUserDto(User user);

    /**
     * Преобразует сущность User в UserDto.
     *
     * @param userDto сущность UserDto
     * @return объект User
     */
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "authoredTasks", ignore = true)
    @Mapping(source = "units", target = "units")
    public abstract User userDtoToUser(UserDto userDto);

}

