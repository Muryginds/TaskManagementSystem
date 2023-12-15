package ru.muryginds.taskmanagement.dto.request;

import lombok.*;

@Data
public class AuthRequestDTO {
    private String email;
    private String password;
}
