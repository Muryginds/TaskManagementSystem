package ru.muryginds.taskmanagement.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.muryginds.taskmanagement.dto.request.CommentRequestDTO;
import ru.muryginds.taskmanagement.dto.response.CommentDTO;
import ru.muryginds.taskmanagement.dto.response.ErrorResponseDTO;

import java.util.List;

@ApiResponse(responseCode = "403", description = "Пользователь не авторизован",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
@Tag(name = "Comment controller")
public interface SwaggerCommentController {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий добавлен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "406", description = "Неверный запрос, не пройдены валидации",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Добавление комментария")
    CommentDTO addComment(@PathVariable long id, @Valid @RequestBody CommentRequestDTO request);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий отправлен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Получение комментария")
    CommentDTO getComment(@PathVariable long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий отредактирован",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "406", description = "Неверный запрос, не пройдены валидации",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Редактирование комментария")
    CommentDTO editComment(@PathVariable long id, @Valid @RequestBody CommentRequestDTO request);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий удален",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Удаление комментария")
    CommentDTO deleteComment(@PathVariable long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий восстановлен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Восстановление комментария")
    CommentDTO recoverComment(@PathVariable long id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список отправлен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "406", description = "Неверный запрос, не пройдены валидации",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @Operation(summary = "Получение списка комментариев")
    List<CommentDTO> getComments(
            @RequestParam(name = "task_id") Long taskId,
            @RequestParam(name = "pagesOffset", defaultValue = "0") @Min(0) int pagesOffset,
            @RequestParam(name = "itemPerPage", defaultValue = "5") @Min(1) int itemPerPage,
            @RequestParam(name = "showDeleted", defaultValue = "false") boolean showDeleted
    );
}
