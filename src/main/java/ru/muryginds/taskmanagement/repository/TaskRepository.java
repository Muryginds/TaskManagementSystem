package ru.muryginds.taskmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.muryginds.taskmanagement.entity.Task;
import ru.muryginds.taskmanagement.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByAuthor(User user, Pageable pageable);

    Page<Task> findByExecutor(User user, Pageable pageable);

}
