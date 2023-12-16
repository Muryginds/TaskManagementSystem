package ru.muryginds.taskmanagement.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.muryginds.taskmanagement.entity.User;

@UtilityClass
public class CurrentUserUtils {
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
