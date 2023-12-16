package ru.muryginds.taskmanagement.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.muryginds.taskmanagement.exception.UserAlreadyExistException;
import ru.muryginds.taskmanagement.exception.UserNotFoundException;
import ru.muryginds.taskmanagement.dto.request.AuthRequestDTO;
import ru.muryginds.taskmanagement.dto.request.RegisterRequestDTO;
import ru.muryginds.taskmanagement.dto.response.UserDTO;
import ru.muryginds.taskmanagement.entity.User;
import ru.muryginds.taskmanagement.mapstruct.UserMapper;
import ru.muryginds.taskmanagement.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO authenticate(AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неверный пароль или имя пользователя");
        }
        var user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));

        var token = jwtService.generateToken(user);
        return UserDTO.builder()
                .id(user.getId())
                .email(request.getEmail())
                .token(token)
                .build();
    }

    @Transactional
    public UserDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistException(request.getEmail());
        }
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return userMapper.userToUserDTO(user)
                .setToken(jwtToken);
    }
}
