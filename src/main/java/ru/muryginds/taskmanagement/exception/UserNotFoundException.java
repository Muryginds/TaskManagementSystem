package ru.muryginds.taskmanagement.exception;

public class UserNotFoundException extends TaskManagerException {
    public UserNotFoundException(String email) {
        super(String.format("Пользователь с почтой: %s не найден", email));
    }
}
