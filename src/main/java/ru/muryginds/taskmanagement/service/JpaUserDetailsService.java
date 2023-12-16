package ru.muryginds.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.muryginds.taskmanagement.exception.UserNotFoundException;
import ru.muryginds.taskmanagement.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
