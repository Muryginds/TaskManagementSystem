package ru.muryginds.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.muryginds.taskmanagement.dto.request.AuthRequestDTO;
import ru.muryginds.taskmanagement.dto.request.RegisterRequestDTO;
import ru.muryginds.taskmanagement.dto.response.UserDTO;
import ru.muryginds.taskmanagement.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public UserDTO authenticate(@RequestBody AuthRequestDTO requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/register")
    public UserDTO authenticate(@RequestBody RegisterRequestDTO requestDto) {
        return authenticationService.register(requestDto);
    }
}
