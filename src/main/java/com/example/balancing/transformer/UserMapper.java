package com.example.balancing.transformer;

import com.example.balancing.dto.user.UserDto;
import com.example.balancing.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserDto entityToDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    public abstract User dtoToEntity(UserDto userDto);

}

