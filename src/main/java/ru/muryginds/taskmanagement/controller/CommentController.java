package ru.muryginds.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.muryginds.taskmanagement.dto.request.CommentRequestDTO;
import ru.muryginds.taskmanagement.dto.response.CommentDTO;
import ru.muryginds.taskmanagement.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}")
    public CommentDTO addComment(@PathVariable long id, @RequestBody CommentRequestDTO request) {
        return commentService.addComment(id, request);
    }

    @GetMapping("/{id}")
    public CommentDTO getComment(@PathVariable long id) {
        return commentService.getComment(id);
    }

    @PutMapping("/{id}")
    public CommentDTO editComment(@PathVariable long id, @RequestBody CommentRequestDTO request) {
        return commentService.editComment(id, request);
    }

    @DeleteMapping("/{id}")
    public CommentDTO deleteComment(@PathVariable long id) {
        return commentService.deleteComment(id);
    }

    @PatchMapping("/{id}/recover")
    public CommentDTO recoverComment(@PathVariable long id) {
        return commentService.recoverComment(id);
    }

    @GetMapping("/all")
    public List<CommentDTO> getComments(
            @RequestParam(name = "task_id") Long taskId,
            @RequestParam(name = "pagesOffset", defaultValue = "0") int pagesOffset,
            @RequestParam(name = "itemPerPage", defaultValue = "5") int itemPerPage,
            @RequestParam(name = "showDeleted", defaultValue = "false") boolean showDeleted) {
        return commentService.getComments(taskId, pagesOffset, itemPerPage, showDeleted);
    }
}
