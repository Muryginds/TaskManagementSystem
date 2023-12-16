package ru.muryginds.taskmanagement.exception;

public class TaskNotFoundException extends TaskManagerException {
    public TaskNotFoundException(Long id) {
        super(String.format("Task with id: %s not found", id));
    }
}
