package com.codmtracker.service;

import com.codmtracker.dto.AuthRequestDto;
import com.codmtracker.dto.AuthResponseDto;
import com.codmtracker.model.User;
import com.codmtracker.repository.RoleRepository;
import com.codmtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto login(AuthRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
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
