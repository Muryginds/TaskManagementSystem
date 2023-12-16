package ru.muryginds.taskmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.muryginds.taskmanagement.enumerated.TaskPriority;
import ru.muryginds.taskmanagement.enumerated.TaskStatus;

@Data
public class TaskRequestDTO {
    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = 1, message = "Заголовок не может быть пустым")
    private String title;
    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = 1, message = "Описание не может быть пустым")
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private Long executorId;
}
