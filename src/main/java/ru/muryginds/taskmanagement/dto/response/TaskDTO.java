package ru.muryginds.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.muryginds.taskmanagement.enumerated.TaskPriority;
import ru.muryginds.taskmanagement.enumerated.TaskStatus;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
@Schema(description = "Модель данных задания")
public class TaskDTO {
    @Schema(description = "id задания")
    private Long id;
    @Schema(description = "Заголовок")
    private String title;
    @Schema(description = "Описание")
    private String description;
    @Schema(description = "Статус")
    private TaskStatus status;
    @Schema(description = "Приоритет")
    private TaskPriority priority;
    @Schema(description = "Автор")
    private UserDTO author;
    @Schema(description = "Исполнитель")
    private UserDTO executor;
    @Schema(description = "Метка активен/удален")
    private boolean isDeleted;
}
