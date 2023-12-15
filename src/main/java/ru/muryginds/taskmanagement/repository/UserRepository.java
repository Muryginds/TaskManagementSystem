package ru.muryginds.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.muryginds.taskmanagement.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);
}
