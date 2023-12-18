package ru.muryginds.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
@Accessors(chain = true)
@Schema(description = "Модель данных пользователя")
public class UserDTO {
    @Schema(description = "id пользователя", example = "2")
    private Long id;
    @Schema(description = "Имя", example = "Дмитрий")
    private String name;
    @Schema(description = "Почта", example = "muryginds@gmail.com")
    private String email;
    @Schema(description = "Токен авторизации")
    private String token;
}
