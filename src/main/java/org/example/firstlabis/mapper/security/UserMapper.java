package org.example.firstlabis.mapper.security;

import org.example.firstlabis.dto.authentication.request.UserDto;
import org.example.firstlabis.mapper.EntityMapper;
import org.example.firstlabis.model.security.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {
}
