package ru.muryginds.taskmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class AuthRequestDTO {
    @NotBlank(message = "Email не может быть пустым")
    @Size(min = 1, message = "Email не может быть пустым")
    private String email;
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 1, message = "Пароль не может быть пустым")
    private String password;
}
