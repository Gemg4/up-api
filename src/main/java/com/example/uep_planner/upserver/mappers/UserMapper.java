package com.example.uep_planner.upserver.mappers;

import com.example.uep_planner.upserver.dtos.user.RegistrationRequest;
import com.example.uep_planner.upserver.dtos.user.UserDto;
import com.example.uep_planner.upserver.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegistrationRequest registrationRequest);
}
