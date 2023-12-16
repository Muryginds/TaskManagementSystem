package ru.muryginds.taskmanagement.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.muryginds.taskmanagement.dto.request.CommentRequestDTO;
import ru.muryginds.taskmanagement.dto.response.CommentDTO;
import ru.muryginds.taskmanagement.entity.Comment;
import ru.muryginds.taskmanagement.exception.CommentModificationException;
import ru.muryginds.taskmanagement.exception.CommentNotFoundException;
import ru.muryginds.taskmanagement.exception.TaskNotFoundException;
import ru.muryginds.taskmanagement.mapstruct.CommentMapper;
import ru.muryginds.taskmanagement.repository.CommentRepository;
import ru.muryginds.taskmanagement.repository.TaskRepository;
import ru.muryginds.taskmanagement.util.CurrentUserUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentDTO addComment(long id, CommentRequestDTO request) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        var currentUser = CurrentUserUtils.getCurrentUser();
        var comment = Comment.builder()
                .text(request.getText())
                .author(currentUser)
                .task(task)
                .build();
        commentRepository.save(comment);
        return commentMapper.commentToCommentDTO(comment);
    }

    public CommentDTO getComment(long id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        return commentMapper.commentToCommentDTO(comment);
    }

    @Transactional
    public CommentDTO editComment(long id, CommentRequestDTO request) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        if (isUserNotAuthor(comment)) {
            throw new CommentModificationException(id);
        }
        comment.setText(request.getText());
        commentRepository.save(comment);
        return commentMapper.commentToCommentDTO(comment);
    }

    private static boolean isUserNotAuthor(Comment comment) {
        var currentUser = CurrentUserUtils.getCurrentUser();
        return !comment.getAuthor().getEmail().equals(currentUser.getEmail());
    }

    @Transactional
    public CommentDTO deleteComment(long id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        if (isUserNotAuthor(comment)) {
            throw new CommentModificationException(id);
        }
        comment.setIsDeleted(true);
        commentRepository.save(comment);
        return commentMapper.commentToCommentDTO(comment);
    }

    @Transactional
    public CommentDTO recoverComment(long id) {
        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        if (isUserNotAuthor(comment)) {
            throw new CommentModificationException(id);
        }
        comment.setIsDeleted(false);
        commentRepository.save(comment);
        return commentMapper.commentToCommentDTO(comment);
    }

    public List<CommentDTO> getComments(Long taskId, int pagesOffset, int itemPerPage, boolean showDeleted) {
        var pageable = PageRequest.of(pagesOffset, itemPerPage);
        var comments = commentRepository.findByTaskIdAndIsDeleted(taskId, showDeleted, pageable);
        return commentMapper.tasksToTaskDTOList(comments.toList());
    }
}
