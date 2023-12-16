package ru.muryginds.taskmanagement.mapstruct;

import org.mapstruct.Mapper;
import ru.muryginds.taskmanagement.dto.response.CommentDTO;
import ru.muryginds.taskmanagement.entity.Comment;

import java.util.List;

@Mapper(uses = {UserMapper.class})
public interface CommentMapper {
    CommentDTO commentToCommentDTO(Comment comment);

    List<CommentDTO> tasksToTaskDTOList(List<Comment> comments);
}
