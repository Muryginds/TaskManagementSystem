package ru.muryginds.taskmanagement.exception;

public class CommentNotFoundException extends TaskManagerException {
    public CommentNotFoundException(long id) {
        super(String.format("Комментарий под номером: %s не найден", id));
    }
}
