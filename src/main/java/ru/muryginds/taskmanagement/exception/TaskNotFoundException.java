package ru.muryginds.taskmanagement.exception;

public class TaskNotFoundException extends TaskManagerException {
    public TaskNotFoundException(long id) {
        super(String.format("Задание под номером: %s не найдено", id));
    }
}
