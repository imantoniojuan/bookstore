package com.anthony.bookstore.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anthony.bookstore.dtos.requests.LoginRequest;
import com.anthony.bookstore.dtos.requests.RegisterRequest;
import com.anthony.bookstore.dtos.responses.RegisterResponse;
import com.anthony.bookstore.entities.User;
import com.anthony.bookstore.repositories.UserRepository;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest input) {
        Optional<User> userOpt = userRepository.findByEmail(input.getEmail());
        if(!userOpt.isPresent() || userOpt.isEmpty()){
            User user = new User();
            user.setEmail(input.getEmail());
            user.setpHash(passwordEncoder.encode(input.getPassword()));
            return new RegisterResponse(userRepository.save(user));
        }
        else
            return new RegisterResponse(userOpt.get());
    }

    public User authenticate(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}
