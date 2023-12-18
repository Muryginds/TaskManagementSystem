package ru.muryginds.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
@Schema(description = "Модель данных комментария")
public class CommentDTO {
    @Schema(description = "id комментария")
    private Long id;
    @Schema(description = "Текст")
    private String text;
    @Schema(description = "Автор")
    private UserDTO author;
    @Schema(description = "Метка активен/удален")
    private boolean isDeleted;
}
