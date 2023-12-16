package ru.muryginds.taskmanagement.mapstruct;

import org.mapstruct.Mapper;
import ru.muryginds.taskmanagement.dto.response.UserDTO;
import ru.muryginds.taskmanagement.entity.User;

@Mapper
public interface UserMapper {
    UserDTO userToUserDTO(User user);
}
