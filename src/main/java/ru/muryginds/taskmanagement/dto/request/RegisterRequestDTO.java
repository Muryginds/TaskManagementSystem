package ru.muryginds.taskmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
  @NotBlank(message = "Имя не может быть пустым")
  @Size(min = 1, message = "Имя не может быть пустым")
  private String name;
  @NotBlank(message = "Пароль не может быть пустым")
  @Size(min = 1, message = "Пароль не может быть пустым")
  private String password;
  @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
          message = "Email должен быть по шаблону aaa@bbb.cc")
  private String email;
}
