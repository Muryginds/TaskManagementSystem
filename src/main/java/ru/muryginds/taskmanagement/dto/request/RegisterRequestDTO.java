package ru.muryginds.taskmanagement.dto.request;

import lombok.Data;

@Data
public class RegisterRequestDTO {
  private String name;
  private String password;
  private String email;
}
