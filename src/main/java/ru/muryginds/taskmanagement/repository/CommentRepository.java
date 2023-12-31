package ru.muryginds.taskmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.muryginds.taskmanagement.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(value = "Comment.authorAndTask", type = EntityGraph.EntityGraphType.FETCH)
    Page<Comment> findByTaskIdAndIsDeleted(long id, boolean isDeleted, Pageable pageable);
}
