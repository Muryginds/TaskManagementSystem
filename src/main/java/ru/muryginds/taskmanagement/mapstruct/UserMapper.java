package ru.muryginds.taskmanagement.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.muryginds.taskmanagement.dto.response.UserDTO;
import ru.muryginds.taskmanagement.entity.User;

@Mapper
public interface UserMapper {
    @Mapping(target = "token", ignore = true)
    UserDTO userToUserDTO(User user);
}
