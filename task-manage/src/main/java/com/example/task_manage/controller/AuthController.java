package com.example.task_manage.controller;

import com.example.task_manage.config.JwtUtil;
import com.example.task_manage.dto.AuthResponse;
import com.example.task_manage.dto.LoginRequest;
import com.example.task_manage.model.User;
import com.example.task_manage.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.register(user, passwordEncoder);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Senha incorreta");
        }

        String token = jwtUtil.generatedToken(user.getEmail());
        return new AuthResponse(token);
    }
}
