package ru.muryginds.taskmanagement.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Модель данных отправки ошибок")
public class ErrorResponseDTO {
    @Schema(description = "Список ошибок", example = "Ошибка входных данных")
    private List<String> errors;
}
