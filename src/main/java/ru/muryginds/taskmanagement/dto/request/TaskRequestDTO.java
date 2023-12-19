package ru.muryginds.taskmanagement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.muryginds.taskmanagement.enumerated.TaskPriority;
import ru.muryginds.taskmanagement.enumerated.TaskStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Модель данных для создания задания")
public class TaskRequestDTO {
    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = 1, max = 100, message = "Длина заголовка от 1 до 100 символов")
    @Schema(description = "Заголовок", example = "Текст нового заголовка")
    private String title;
    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = 1, message = "Длина описания не меньше 1 символа")
    @Schema(description = "Описание", example = "Текст описания задания")
    private String description;
    @Schema(description = "Статус задания", example = "IN_PROGRESS")
    private TaskStatus status;
    @Schema(description = "Приоритет выполнения", example = "LOW")
    private TaskPriority priority;
    @Schema(description = "id исполнителя", example = "3")
    private Long executorId;
}
