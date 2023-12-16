package ru.muryginds.taskmanagement.exception;

public class CommentModificationException extends TaskManagerException {
    public CommentModificationException(long id) {
        super(String.format("Этот пользователь не может редактировать комментарий с id %s", id));
    }
}
