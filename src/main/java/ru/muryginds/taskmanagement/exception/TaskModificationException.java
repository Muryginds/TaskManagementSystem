package ru.muryginds.taskmanagement.exception;

public class TaskModificationException extends TaskManagerException {
    public TaskModificationException(long id) {
        super(String.format("Этот пользователь не может редактировать задание с id %s", id));
    }
}
