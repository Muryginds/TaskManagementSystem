package ru.muryginds.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.muryginds.taskmanagement.entity.Comment;
import ru.muryginds.taskmanagement.entity.Task;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTask(Task task);

}
