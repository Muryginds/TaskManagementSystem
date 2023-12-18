package ru.muryginds.taskmanagement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Модель данных для создания комментария")
public class CommentRequestDTO {
    @NotNull(message = "Текст не может быть пустым")
    @Size(min = 1, max = 500, message = "Длина текста от 1 до 500 символов")
    @Schema(description = "Текст комментария", example = "Текст нового комментария")
    private String text;
}
