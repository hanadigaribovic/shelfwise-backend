package com.shelfwise.shelfwise.service;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.shelfwise.shelfwise.dto.auth.AuthResponse;
import com.shelfwise.shelfwise.dto.auth.LoginRequest;
import com.shelfwise.shelfwise.dto.auth.RegisterRequest;
import com.shelfwise.shelfwise.entity.UserEntity;
import com.shelfwise.shelfwise.repository.UserRepository;
import com.shelfwise.shelfwise.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        // Provjera da li email postoji
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

        // Kreiranje korisnika
        UserEntity user = UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        // Generisanje tokena
        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .uid(user.getUid())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .uid(user.getUid())
                .build();
    }
}
