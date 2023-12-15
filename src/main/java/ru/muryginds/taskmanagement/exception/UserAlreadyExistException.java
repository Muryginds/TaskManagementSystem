package ru.muryginds.taskmanagement.exception;

public class UserAlreadyExistException extends TaskManagerException {
    public UserAlreadyExistException(String email) {
        super(String.format("Пользователь с email: %s уже существует", email));
    }
}
