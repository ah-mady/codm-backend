package com.codmtracker.service;

import com.codmtracker.dto.AuthRequestDto;
import com.codmtracker.dto.AuthResponseDto;
import com.codmtracker.exception.CustomException;
import com.codmtracker.model.Role;
import com.codmtracker.model.User;
import com.codmtracker.repository.RoleRepository;
import com.codmtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto signup(AuthRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new CustomException("Email already registered", 409);

        long userCount = userRepository.count();
        Role adminRole = roleRepository.findByName("ADMIN").orElseGet(() -> {
            Role r = new Role();
            r.setName("ADMIN");
            return roleRepository.save(r);
        });

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .emailVerified(true)
                .createdAt(Instant.now())
                .roles(Set.of(adminRole))
                .build();
        userRepository.save(user);

        return AuthResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .accessToken("dummy-token")
                .refreshToken("")
                .build();
    }

    @Override
    public AuthResponseDto login(AuthRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("User not found", 404));
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash()))
            throw new CustomException("Invalid credentials", 401);
        boolean valid = passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
        if (!valid) throw new RuntimeException("Invalid credentials");
        AuthResponseDto authResponseDto = AuthResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .accessToken("dummy-token")
                .refreshToken("")
                .build();
        return authResponseDto;
    }

    @Override
    public void sendEmailConfirmation(String email) {
    }

    @Override
    public void confirmEmail(String token) {
    }

    @Override
    public void sendPasswordReset(String email) {
    }

    @Override
    public void resetPassword(String token, String newPassword) {
    }
}
