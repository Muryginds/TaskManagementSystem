package ru.muryginds.taskmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDTO {
    @NotNull(message = "Текст не может быть пустым")
    @Size(min = 1, message = "Текст не может быть пустым")
    private String text;
}
