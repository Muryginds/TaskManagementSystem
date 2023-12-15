package ru.muryginds.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String token;
}
