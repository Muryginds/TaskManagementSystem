package ru.muryginds.taskmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class AuthRequestDTO {
    @NotBlank(message = "Email не может быть пустым")
    @Size(min = 1, max = 50, message = "Длина email от 1 до 50 символов")
    private String email;
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 1, max = 50, message = "Длина пароля от 1 до 50 символов")
    private String password;
}
