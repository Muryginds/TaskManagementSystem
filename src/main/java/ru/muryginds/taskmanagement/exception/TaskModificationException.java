package ru.muryginds.taskmanagement.exception;

public class TaskModificationException extends TaskManagerException {
    public TaskModificationException(long id) {
        super(String.format("Task with id %s can not be modified by current user", id));
    }
}
