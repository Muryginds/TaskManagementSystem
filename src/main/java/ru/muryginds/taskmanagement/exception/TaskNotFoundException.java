package ru.muryginds.taskmanagement.exception;

public class TaskNotFoundException extends TaskManagerException {
    public TaskNotFoundException(Long id) {
        super(String.format("Задание под номером: %s не найдено", id));
    }
}
