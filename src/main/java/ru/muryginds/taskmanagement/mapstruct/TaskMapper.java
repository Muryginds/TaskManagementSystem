package ru.muryginds.taskmanagement.mapstruct;

import org.mapstruct.Mapper;
import ru.muryginds.taskmanagement.dto.response.TaskDTO;
import ru.muryginds.taskmanagement.entity.Task;

import java.util.List;

@Mapper(uses = {UserMapper.class})
public interface TaskMapper {
    TaskDTO taskToTaskDTO(Task task);

    List<TaskDTO> tasksToTaskDTOList(List<Task> tasks);
}
