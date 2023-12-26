package ru.muryginds.taskmanagement.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import ru.muryginds.taskmanagement.dto.request.AuthRequestDTO;
import ru.muryginds.taskmanagement.dto.request.RegisterRequestDTO;
import ru.muryginds.taskmanagement.dto.response.ErrorResponseDTO;
import ru.muryginds.taskmanagement.dto.response.UserDTO;

@Tag(name = "Authentication")
public interface SwaggerAuthenticationController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь авторизован",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "Неверный пароль или имя пользователя",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "406", description = "Неверный запрос, не пройдены валидации",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Авторизация пользователя")
    UserDTO authenticate(@Valid @RequestBody AuthRequestDTO requestDto);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Аккаунт зарегистрирован",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "406", description = "Неверный запрос, не пройдены валидации",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Регистрация пользователя")
    UserDTO register(@Valid @RequestBody RegisterRequestDTO requestDto);
}
