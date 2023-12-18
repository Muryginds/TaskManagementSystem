package ru.muryginds.taskmanagement.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.muryginds.taskmanagement.dto.request.CommentRequestDTO;
import ru.muryginds.taskmanagement.dto.response.CommentDTO;
import ru.muryginds.taskmanagement.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController implements ru.muryginds.taskmanagement.controller.swagger.SwaggerCommentController {
    private final CommentService commentService;

    @Override
    @PostMapping("/{id}")
    public CommentDTO addComment(@PathVariable long id, @Valid @RequestBody CommentRequestDTO request) {
        return commentService.addComment(id, request);
    }

    @Override
    @GetMapping("/{id}")
    public CommentDTO getComment(@PathVariable long id) {
        return commentService.getComment(id);
    }

    @Override
    @PutMapping("/{id}")
    public CommentDTO editComment(@PathVariable long id, @Valid @RequestBody CommentRequestDTO request) {
        return commentService.editComment(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public CommentDTO deleteComment(@PathVariable long id) {
        return commentService.deleteComment(id);
    }

    @Override
    @PatchMapping("/{id}/recover")
    public CommentDTO recoverComment(@PathVariable long id) {
        return commentService.recoverComment(id);
    }

    @Override
    @GetMapping("/all")
    public List<CommentDTO> getComments(
            @RequestParam(name = "task_id") Long taskId,
            @RequestParam(name = "pagesOffset", defaultValue = "0") @Min(0) int pagesOffset,
            @RequestParam(name = "itemPerPage", defaultValue = "5") @Min(1) int itemPerPage,
            @RequestParam(name = "showDeleted", defaultValue = "false") boolean showDeleted) {
        return commentService.getComments(taskId, pagesOffset, itemPerPage, showDeleted);
    }
}
