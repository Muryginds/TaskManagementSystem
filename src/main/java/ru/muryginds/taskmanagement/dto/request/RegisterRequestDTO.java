package ru.muryginds.taskmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
  @NotBlank(message = "Имя не может быть пустым")
  @Size(min = 1, max = 200, message = "Длина имени от 1 до 200 символов")
  private String name;
  @NotBlank(message = "Пароль не может быть пустым")
  @Size(min = 1, max = 50, message = "Длина пароля от 1 до 50 символов")
  private String password;
  @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
          message = "Email должен быть по шаблону aaa@bbb.cc")
  private String email;
}
