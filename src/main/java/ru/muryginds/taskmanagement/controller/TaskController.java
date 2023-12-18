package ru.muryginds.taskmanagement.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.muryginds.taskmanagement.dto.request.TaskRequestDTO;
import ru.muryginds.taskmanagement.dto.response.TaskDTO;
import ru.muryginds.taskmanagement.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController implements ru.muryginds.taskmanagement.controller.swagger.SwaggerTaskController {
    private final TaskService taskService;

    @Override
    @PostMapping("")
    public TaskDTO addTask(@Valid @RequestBody TaskRequestDTO request) {
        return taskService.addTask(request);
    }

    @Override
    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable long id) {
        return taskService.getTask(id);
    }

    @Override
    @PutMapping("/{id}")
    public TaskDTO editTask(@PathVariable long id, @Valid @RequestBody TaskRequestDTO request) {
        return taskService.editTask(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public TaskDTO deleteTask(@PathVariable long id) {
        return taskService.deleteTask(id);
    }

    @Override
    @PatchMapping("/{id}/recover")
    public TaskDTO recoverTask(@PathVariable long id) {
        return taskService.recoverTask(id);
    }

    @Override
    @GetMapping("/all")
    public List<TaskDTO> getTasks(
            @RequestParam(name = "author_id", required = false) Long authorId,
            @RequestParam(name = "executor_id", required = false) Long executorId,
            @RequestParam(name = "pagesOffset", defaultValue = "0") @Min(0) int pagesOffset,
            @RequestParam(name = "itemPerPage", defaultValue = "5") @Min(1) int itemPerPage,
            @RequestParam(name = "showDeleted", defaultValue = "false") boolean showDeleted) {
        return taskService.getTasks(authorId, executorId, pagesOffset, itemPerPage, showDeleted);
    }
}
