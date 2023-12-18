package ru.muryginds.taskmanagement.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.muryginds.taskmanagement.dto.request.TaskRequestDTO;
import ru.muryginds.taskmanagement.dto.response.CommentDTO;
import ru.muryginds.taskmanagement.dto.response.ErrorResponseDTO;
import ru.muryginds.taskmanagement.dto.response.TaskDTO;

import java.util.List;

@ApiResponse(responseCode = "403", description = "Пользователь не авторизован",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
@Tag(name = "Task controller")
public interface SwaggerTaskController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задание добавлено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "406", description = "Неверный запрос, не пройдены валидации",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Добавление задания")
    TaskDTO addTask(TaskRequestDTO request);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задание отправлено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Получение задания")
    TaskDTO getTask(long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задание отредактировано",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "406", description = "Неверный запрос, не пройдены валидации",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Редактирование задания")
    TaskDTO editTask(long id, TaskRequestDTO request);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задание удалено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Удаление задания")
    TaskDTO deleteTask(long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задание восстановлено",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Восстановление задания")
    TaskDTO recoverTask(long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список отправлен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "406", description = "Неверный запрос, не пройдены валидации",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Получение списка заданий")
    List<TaskDTO> getTasks(
            Long authorId,
            Long executorId,
            int pagesOffset,
            int itemPerPage,
            boolean showDeleted
    );
}
