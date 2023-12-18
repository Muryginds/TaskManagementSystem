package ru.muryginds.taskmanagement.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.muryginds.taskmanagement.controller.swagger.SwaggerAuthenticationController;
import ru.muryginds.taskmanagement.dto.request.AuthRequestDTO;
import ru.muryginds.taskmanagement.dto.request.RegisterRequestDTO;
import ru.muryginds.taskmanagement.dto.response.UserDTO;
import ru.muryginds.taskmanagement.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController implements SwaggerAuthenticationController {
    private final AuthenticationService authenticationService;

    @Override
    @PostMapping("/authenticate")
    public UserDTO authenticate(@Valid @RequestBody AuthRequestDTO requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @Override
    @PostMapping("/register")
    public UserDTO register(@Valid @RequestBody RegisterRequestDTO requestDto) {
        return authenticationService.register(requestDto);
    }
}
