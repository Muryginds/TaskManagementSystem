package ru.muryginds.taskmanagement.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.muryginds.taskmanagement.dto.request.TaskRequestDTO;
import ru.muryginds.taskmanagement.dto.response.TaskDTO;
import ru.muryginds.taskmanagement.entity.Task;
import ru.muryginds.taskmanagement.entity.User;
import ru.muryginds.taskmanagement.exception.TaskModificationException;
import ru.muryginds.taskmanagement.exception.TaskNotFoundException;
import ru.muryginds.taskmanagement.mapstruct.TaskMapper;
import ru.muryginds.taskmanagement.repository.TaskRepository;
import ru.muryginds.taskmanagement.repository.UserRepository;
import ru.muryginds.taskmanagement.util.CurrentUserUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskDTO addTask(TaskRequestDTO request) {
        var currentUser = CurrentUserUtils.getCurrentUser();
        var executor = userRepository.findById(request.getExecutorId()).orElse(null);

        var task = Task.builder()
                .description(request.getDescription())
                .title(request.getTitle())
                .status(request.getStatus())
                .priority(request.getPriority())
                .executor(executor)
                .author(currentUser)
                .build();
        taskRepository.save(task);

        return taskMapper.taskToTaskDTO(task);
    }

    public TaskDTO getTask(long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.taskToTaskDTO(task);
    }

    @Transactional
    public TaskDTO editTask(long id, TaskRequestDTO request) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        var currentUser = CurrentUserUtils.getCurrentUser();
        if (!isUserCanModifyTask(currentUser, task)) {
            throw new TaskModificationException(id);
        }
        if (isUserExecutorOfTask(currentUser, task)) {
            task.setStatus(request.getStatus());
            taskRepository.save(task);
            return taskMapper.taskToTaskDTO(task);
        }
        var updatedExecutor = userRepository.findById(request.getExecutorId()).orElse(null);
        task
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setStatus(request.getStatus())
                .setPriority(request.getPriority())
                .setExecutor(updatedExecutor);
        taskRepository.save(task);
        return taskMapper.taskToTaskDTO(task);
    }

    private boolean isUserAuthorOfTask(User user, Task task) {
        return user.getEmail().equals(task.getAuthor().getEmail());
    }

    private boolean isUserExecutorOfTask(User user, Task task) {
        return task.getExecutor() != null && user.getEmail().equals(task.getExecutor().getEmail());
    }

    private boolean isUserCanModifyTask(User user, Task task) {
        return isUserAuthorOfTask(user, task) || isUserExecutorOfTask(user, task);
    }

    @Transactional
    public TaskDTO deleteTask(long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        var currentUser = CurrentUserUtils.getCurrentUser();
        if (!isUserAuthorOfTask(currentUser, task)) {
            throw new TaskModificationException(id);
        }
        task.setIsDeleted(true);
        taskRepository.save(task);
        return taskMapper.taskToTaskDTO(task);
    }

    @Transactional
    public TaskDTO recoverTask(long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        var currentUser = CurrentUserUtils.getCurrentUser();
        if (!isUserAuthorOfTask(currentUser, task)) {
            throw new TaskModificationException(id);
        }
        task.setIsDeleted(false);
        taskRepository.save(task);
        return taskMapper.taskToTaskDTO(task);
    }

    public List<TaskDTO> getTasks(
            Long authorId, Long executorId, int pagesOffset, int itemPerPage, boolean showDeleted) {
        var pageable = PageRequest.of(pagesOffset, itemPerPage);
        if (authorId != null) {
            var tasks = taskRepository.findByAuthorIdAndIsDeleted(authorId, showDeleted, pageable);
            return taskMapper.tasksToTaskDTOList(tasks.toList());
        }
        if (executorId != null) {
            var tasks = taskRepository.findByExecutorIdAndIsDeleted(executorId, showDeleted, pageable);
            return taskMapper.tasksToTaskDTOList(tasks.toList());
        }
        var tasks = taskRepository.findByIsDeleted(showDeleted, pageable);
        return taskMapper.tasksToTaskDTOList(tasks.toList());
    }
}
