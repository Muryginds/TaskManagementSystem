package ru.muryginds.taskmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.muryginds.taskmanagement.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByAuthorIdAndIsDeleted(long id, boolean deleted, Pageable pageable);

    Page<Task> findByExecutorIdAndIsDeleted(long id, boolean deleted, Pageable pageable);

    Page<Task> findByIsDeleted(boolean deleted, Pageable pageable);
}
