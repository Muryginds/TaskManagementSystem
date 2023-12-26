package ru.muryginds.taskmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.muryginds.taskmanagement.entity.Task;

import static org.springframework.data.jpa.repository.EntityGraph.*;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @EntityGraph(value = "Task.authorAndExecutor", type = EntityGraphType.FETCH)
    Page<Task> findByAuthorIdAndIsDeleted(long id, boolean deleted, Pageable pageable);

    @EntityGraph(value = "Task.authorAndExecutor", type = EntityGraphType.FETCH)
    Page<Task> findByExecutorIdAndIsDeleted(long id, boolean deleted, Pageable pageable);

    @EntityGraph(value = "Task.authorAndExecutor", type = EntityGraphType.FETCH)
    Page<Task> findByIsDeleted(boolean deleted, Pageable pageable);
}
