package com.example.task_manage.service;

import com.example.task_manage.model.User;
import com.example.task_manage.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Agora recebe o PasswordEncoder como parâmetro
    public User register(User user, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
