package ru.muryginds.taskmanagement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Schema(description = "Модель данных для авторизации")
public class AuthRequestDTO {
    @Schema(description = "Пользовательский email", example = "muryginds@gmail.com")
    @NotNull(message = "Поле обязательно к заполнению")
    private String email;
    @Schema(description = "Пользовательский пароль")
    @NotNull(message = "Поле обязательно к заполнению")
    private String password;
}
